package cn.learn.beans.processor.impl;

import cn.learn.aop.aspect.advice.Advice;
import cn.learn.aop.aspect.aspector.AspectJAspetor;
import cn.learn.aop.aspect.aspector.Aspector;
import cn.learn.aop.aspect.interceptor.MethodAdviceInterceptor;
import cn.learn.aop.aspect.pointcut.Pointcutor;
import cn.learn.aop.entity.AopProxyConfig;
import cn.learn.aop.proxy.autoproxy.ProxyFactory;
import cn.learn.aware.BeanFactoryAware;
import cn.learn.beanfactory.BeanFactory;
import cn.learn.beanfactory.factory.DefaultListableBeanFactory;
import cn.learn.beans.processor.InstantiationAwareBeanPostProcessor;
import cn.learn.exception.BeansException;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class DefaultAopProxyCreateProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    public DefaultAopProxyCreateProcessor(DefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    private static final Set<Class<?>> INFRASTRUCTURE_CLASSES = new HashSet<>();

    static {
        // 通知
        INFRASTRUCTURE_CLASSES.add(Advice.class);
        // 通知拦截器
        INFRASTRUCTURE_CLASSES.add(MethodAdviceInterceptor.class);
        // 切面
        INFRASTRUCTURE_CLASSES.add(Aspector.class);
        // 切入点
        INFRASTRUCTURE_CLASSES.add(Pointcutor.class);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        log.debug("代理检查: {}, class: {}", beanName, beanClass.getName());

        // 基础设施类是 Spring AOP 的核心组成部分，不能被代理。
        if (isInfrastructureClass(beanClass)) {
            log.debug("跳过代理: {}, class: {}，由于其为AOP基础组成类，故无需代理。", beanName, beanClass.getName());
            return null;
        }

        // 获取所有的切面器，用于后续的代理处理。
        Collection<AspectJAspetor> aspectors = beanFactory.getBeansOfType(AspectJAspetor.class).values();

        // 对于当前的bean，遍历处理所有的切面处理器
        for (AspectJAspetor aspector : aspectors) {
            Pointcutor pointcutor = aspector.getPointcutor();
            /** note:
             *   类类型如果匹配，则证明需要创建代理类实现aop，但是具体哪个方法需要进行aop，
             *   是交给 JdkDynamicAopProxy#invoke 来决定。*/
            // 检查切面类是否匹配，不匹配直接跳过
            if (!pointcutor.classFilter(beanClass)) {
                continue;
            }

            log.debug("{}, class:{} - 匹配切面: {}，需要进行代理。", beanName, beanClass.getName(), aspector.getClass().getName());
            // 获取目标对象 targetSource
            AopProxyConfig.TargetSource targetSource = null;
            try {
//                targetSource = new AopProxyConfig.TargetSource(beanClass.getDeclaredConstructor().newInstance());
                targetSource = new AopProxyConfig.TargetSource(bean);
                log.debug("创建代理的目标对象: {}, class: {}", beanName, beanClass.getName());
            } catch (Exception e) {
                throw new RuntimeException(this.getClass().getName() + "未能初始化类对象: " + beanClass.getName(), e);
            }

            // 创建 AopProxyConfig
            AopProxyConfig config = new AopProxyConfig();
            config.setTargetSource(targetSource);
            config.setMethodInterceptor(aspector.getAdviceInterceptor());
            config.setMethodMatcher(pointcutor);

            log.debug("完成代理，返回代理对象。");
            // 获取到代理对象
            return ProxyFactory.getProxy(config);
        }

        log.debug("{}, class:{} - 不匹配任何切面，所以无需代理。", beanName, beanClass.getName());
        return null;
    }

    public boolean isInfrastructureClass(Class<?> beanClass) {
        for (Class<?> infrastructureClass : INFRASTRUCTURE_CLASSES) {
            if (infrastructureClass.isAssignableFrom(beanClass)) {
                return true;
            }
        }
        return false;
    }

}