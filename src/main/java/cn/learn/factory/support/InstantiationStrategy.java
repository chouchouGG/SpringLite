package cn.learn.factory.support;

import cn.learn.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-05 10:03
 **/
public interface InstantiationStrategy {

    /**
     * 实例化 Bean 对象
     * <p>
     *     对 Constructor 你可能会有一点陌生，它是 java.lang.reflect 包下的 Constructor 类，里面包含了一些必要的类信息
     * </p>
     *
     * @param beanDefinition Bean 的定义信息
     * @param beanName       Bean 的名称
     * @param ctor           构造函数，如果有特定的构造函数需要使用，否则为 null
     * @param args           构造函数参数，如果有特定的构造函数需要使用，否则为 null
     * @return 实例化后的 Bean 对象
     */
    Object instantiate(BeanDefinition beanDefinition, Constructor ctor, Object[] args);
}
