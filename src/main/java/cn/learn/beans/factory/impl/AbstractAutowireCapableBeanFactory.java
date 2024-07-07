package cn.learn.beans.factory.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.learn.beans.entity.BeanDefinition;
import cn.learn.beans.exception.BeansException;
import cn.learn.beans.entity.BeanReference;
import cn.learn.beans.entity.PropertyValue;
import cn.learn.beans.factory.AutowireCapableBeanFactory;
import cn.learn.beans.processor.BeanPostProcessor;
import cn.learn.beans.factory.support.instantiate.InstantiationStrategy;
import cn.learn.beans.factory.support.instantiate.SimpleInstantiationStrategy;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Constructor;
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

    // 策略模式：默认使用【简单实例化策略】
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
        // 1. 实例化 Bean
        bean = createBeanInstance(beanDefinition, args);

        // 2. 给 Bean 填充属性
        setConfigPropertyValues(beanName, bean, beanDefinition);

        // 3. 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
        bean = initBean(beanName, bean, beanDefinition);

        // 4. 缓存单例 Bean
        addSingleton(beanName, bean);

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

    private Object initBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 1. 执行 BeanPostProcessor 的前置处理
        Object wrappedBean = beforeProcess(bean, beanName);

        // TODO：待完成内容：invokeInitMethods(beanName, wrappedBean, beanDefinition);
        invokeOriginInit(beanName, wrappedBean, beanDefinition);

        // 2. 执行 BeanPostProcessor 的后置处理
        wrappedBean = afterProcess(wrappedBean, beanName);

        return wrappedBean;
    }

    /**
     * 调用Bean对象原始的初始化方法
     */
    private void invokeOriginInit(String beanName, Object wrappedBean, BeanDefinition beanDefinition) {

    }

    @Override
    public Object beforeProcess(Object bean, String beanName) throws BeansException {
        Object result = bean;
        // 回调：循环处理所有 BeanPostProcessor 的【前置处理】方法
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            result = processor.postProcessBeforeInitialization(result, beanName);
        }
        return result;
    }

    @Override
    public Object afterProcess(Object bean, String beanName) throws BeansException {
        Object result = bean;
        // 回调：循环处理所有 BeanPostProcessor 的【后置处理】方法
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            result = processor.postProcessAfterInitialization(result, beanName);
        }
        return result;
    }

}
