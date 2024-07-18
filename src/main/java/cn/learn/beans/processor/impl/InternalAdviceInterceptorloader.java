package cn.learn.beans.processor.impl;

import cn.hutool.core.util.StrUtil;
import cn.learn.aop.aspect.advice.Advice;
import cn.learn.aop.aspect.advice.MethodAroundAdvice;
import cn.learn.aop.aspect.advice.MethodBeforeAdvice;
import cn.learn.aop.aspect.interceptor.MethodAroundAdviceInterceptor;
import cn.learn.aop.aspect.interceptor.MethodBeforeAdviceInterceptor;
import cn.learn.aware.BeanFactoryAware;
import cn.learn.beanfactory.BeanFactory;
import cn.learn.beanfactory.factory.DefaultListableBeanFactory;
import cn.learn.beans.entity.BeanDefinition;
import cn.learn.beans.processor.InstantiationAwareBeanPostProcessor;
import cn.learn.beans.registry.BeanDefinitionRegistry;
import cn.learn.exception.BeansException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-17 14:46
 **/
public class InternalAdviceInterceptorloader implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private static final Map<Class<?>, Class<?>> ADVICE_INTERCEPTOR_MAP = new HashMap<>();

    private DefaultListableBeanFactory beanFactory;

    public InternalAdviceInterceptorloader(DefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    static {
        ADVICE_INTERCEPTOR_MAP.put(MethodAroundAdvice.class, MethodAroundAdviceInterceptor.class);
        ADVICE_INTERCEPTOR_MAP.put(MethodBeforeAdvice.class, MethodBeforeAdviceInterceptor.class);
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {

        if (!(Advice.class.isAssignableFrom(beanClass))) {
            return null;
        }

        Type[] genericInterfaces = beanClass.getGenericInterfaces();
        Object bean = null;
        for (Type genericInterface : genericInterfaces) {
            // 如果当前接口不是 Advice 的子类或实现类，则返回 null，不做任何处理
            if (!(Advice.class.isAssignableFrom((Class<?>) genericInterface))) {
                continue;
            }
            if (ADVICE_INTERCEPTOR_MAP.containsKey(genericInterface)) {
                Class<?> interceptorClass = ADVICE_INTERCEPTOR_MAP.get(genericInterface);
                try {
                    BeanDefinition adviceDefinition = beanFactory.getBeanDefinition(beanName);
                    Object advice = adviceDefinition.getBeanClass().newInstance();
                    bean = interceptorClass.getDeclaredConstructor((Class<?>) genericInterface).newInstance(advice);
                    beanFactory.registerSingleton(beanName, bean);
                    break; // 一旦找到并创建了拦截器，直接退出循环
                } catch (Exception e) {
                    throw new BeansException("未能实例化拦截器: " + interceptorClass.getName() + "通知：" + beanName, e);
                }
            }
        }
        return bean;
    }

    private String getDefaultBeanName(Class<?> beanClass) {
        return StrUtil.lowerFirst(beanClass.getSimpleName());
    }

    private void registerBeanDefinition(BeanDefinitionRegistry registry, Class<?> beanClass) {
        String beanName = getDefaultBeanName(beanClass);
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);
        if (!registry.containsBeanDefinition(beanName)) {
            registry.registerBeanDefinition(beanName, beanDefinition);
        }
    }


}
