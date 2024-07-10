package cn.learn.factory.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.learn.beans.support.DisposableBean;
import cn.learn.beans.support.DisposableBeanAdapter;
import cn.learn.beans.support.InitializingBean;
import cn.learn.beans.aware.Aware;
import cn.learn.beans.aware.BeanClassLoaderAware;
import cn.learn.beans.aware.BeanFactoryAware;
import cn.learn.beans.aware.BeanNameAware;
import cn.learn.beans.entity.BeanDefinition;
import cn.learn.exception.BeansException;
import cn.learn.beans.entity.BeanReference;
import cn.learn.beans.entity.PropertyValue;
import cn.learn.factory.AutowireCapableBeanFactory;
import cn.learn.beans.processor.BeanPostProcessor;
import cn.learn.beans.instantiate.InstantiationStrategy;
import cn.learn.beans.instantiate.SimpleInstantiationStrategy;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-05 00:59
 **/
@Getter
@Setter
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    // note：策略模式：默认使用【简单实例化策略 SimpleInstantiationStrategy】
    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    /**
     * 问：newInstance() 实例化方式并没有考虑带参构造函数的入参，怎么去实例化含有构造函数的对象？
     * <p>
     * 答：方法有两种，一个是基于 Java 本身自带的方法 DeclaredConstructor，另外一个是使用 Cglib 来动态创建 Bean 对象。此处采用的是第一种方法。
     * </p>
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Object bean = null;

        // 1. 执行构造方法，实例化 Bean
        bean = createBeanInstance(beanDefinition, args);

        // 2. 设置属性值（根据配置文件填充属性值，会覆盖实例化时的参数 args 设置）
        setConfigPropertyValues(beanName, bean, beanDefinition);

        // 3. 设置感知层
        setBeanAware(beanName, bean);

        // 3. 执行【初始化方法】，并执行处理器的【前置和后置方法】
        bean = initBeanAndProcess(beanName, bean, beanDefinition);

        // 注册【销毁方法】
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        // 4. 【缓存】只有Bean的作用域是单例时，才缓存该单例Bean
        if (beanDefinition.isSingleton()) {
            addSingleton(beanName, bean);
        }

        return bean;
    }

    private Object createBeanInstance(BeanDefinition beanDefinition, Object[] args) {
        Constructor constructorToUse = null;
        // 获取到 Bean 所有的构造函数
        Constructor<?>[] declaredConstructors = beanDefinition.getBeanClass().getDeclaredConstructors();
        // 1. 如果 args 为 null，直接使用无参构造函数实例化 Bean
        if (args == null) {
            return instantiationStrategy.instantiate(beanDefinition, null, null);
        }
        // 查找匹配参数数量的构造函数
        for (Constructor<?> ctor : declaredConstructors) {
            if (ctor.getParameterTypes().length == args.length) {
                constructorToUse = ctor;
                break;
            }
        }
        // 2. args 为 null，constructorToUse 不为 null，即没有找到匹配的构造函数
        if (constructorToUse == null) {
            throw new BeansException("不存在与传入参数 args 匹配的构造函数，args: " + Arrays.toString(args));
        }
        // 3. args 和 constructorToUse 都不为 null，则使用找到的构造函数实例化 Bean
        return getInstantiationStrategy().instantiate(beanDefinition, constructorToUse, args);
    }

    /**
     * 依赖注入的核心方法
     *
     * @param bean           实例化后待设置属性值的Bean
     * @param beanDefinition Bean定义
     */
    private void setConfigPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            // 获取 pvArray——属性值数组
            PropertyValue[] pvArray = beanDefinition.getPropertyValues().getPropertyValuesArray();
            for (PropertyValue propertyValue : pvArray) {

                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                // fixme：暂时没有处理循环依赖的问题，后续处理
                // 判断属性值是否为 BeanReference 类型（即该属性是否为另一个 Bean 对象）
                if (value instanceof BeanReference) {
                    // 如果是属性值为 Bean，则先递归式的实例化该 Bean
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                // 属性填充
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("配置属性值错误：" + beanName);
        }
    }

    private void setBeanAware(String beanName, Object bean) {
        if (bean instanceof Aware) {
            // 1. BeanName感知
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
            // 2. BeanFactory感知
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            // 3. BeanClassLoader感知
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
        }
    }

    private Object initBeanAndProcess(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 1. 前置处理
        Object wrappedBean = beforeProcessOfAllProcessor(bean, beanName);

        // 2. 初始化方法
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("调用Bean对象的原始初始化方法失败，beanName：[" + beanName + "]", e);
        }

        // 3. 后置处理
        wrappedBean = afterProcessOfAllProcessor(wrappedBean, beanName);
        return wrappedBean;
    }

    private void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 原型Bean实例的销毁由使用者控制，Spring容器无法跟踪这些Bean的生命周期。因此，Spring不负责调用原型Bean的销毁方法。
        if (beanDefinition.isPrototype()) {
            return;
        }

        // 如果通过 (1. 接口方式) 或者 (2. 配置XML文件方式) 定义了销毁方法，则进行销毁方法的注册
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    /**
     * 调用Bean对象的初始化方法，对外提供两种方式（根据 invokeInitMethods 的逻辑可知：接口的优先级 > 配置的优先级）：
     * <p> 1. 在 xml 中配置初始化方法。</p>
     * <p> 2. 通过实现接口的方式处理。</p>
     */
    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        // 1. 实现接口 InitializingBean
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        // 2. 注解配置 init-method（判断是为了避免二次执行初始化）
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName) && !(bean instanceof InitializingBean)) {
            // 如果Bean设置了初始化方法，并且Bean实现了相应接口
            Method initMethod = null;
            try {
                initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            } catch (NoSuchMethodException e) {
                throw new BeansException("在bean对象 '" + beanName + "'上，不能找到一个命名为 '" + initMethodName + "' 的初始化方法");
            }
            // 调用初始化方法
            initMethod.invoke(bean);
        }
    }

    @Override
    public Object beforeProcessOfAllProcessor(Object bean, String beanName) throws BeansException {
        Object result = bean;
        // 回调：循环处理所有 BeanPostProcessor 的【前置处理】方法
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (null == current) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object afterProcessOfAllProcessor(Object bean, String beanName) throws BeansException {
        Object result = bean;
        // 回调：循环处理所有 BeanPostProcessor 的【后置处理】方法
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (null == current) {
                return result;
            }
            result = current;
        }
        return result;
    }

}
