package cn.learn.factory.support;

import cn.learn.factory.config.BeanDefinition;
import cn.learn.exception.BeansException;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-05 00:59
 **/
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    // 策略模式：默认使用【简单实例化策略】
    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    /**
     *  问：newInstance() 实例化方式并没有考虑带参构造函数的入参，怎么去实例化含有构造函数的对象？
     *  <p>
     *  答：方法有两种，一个是基于 Java 本身自带的方法 DeclaredConstructor，另外一个是使用 Cglib 来动态创建 Bean 对象。此处采用的是第一种方法。
     *  </p>
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Object bean = null;
        // 1. 实例化 Bean
        bean = createBeanInstance(beanDefinition, args);
        // 2. 缓存单例 Bean
        addSingleton(beanName, bean);
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, Object[] args) {
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

}
