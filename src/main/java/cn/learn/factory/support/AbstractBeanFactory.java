package cn.learn.factory.support;

import cn.learn.factory.BeanFactory;
import cn.learn.factory.config.BeanDefinition;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-04 23:45
 **/
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) {
         return doGetBean(name, args);
    }

    protected <T> T doGetBean(final String name, final Object[] args) {
        // 尝试从单例缓存中获取 Bean
        Object bean = getSingleton(name);
        if (bean != null) {
            return (T) bean;
        }
        // 1. 获取 Bean 定义
        BeanDefinition beanDefinition = getBeanDefinition(name);
        // 2. 创建 Bean 实例
        return (T) createBean(name, beanDefinition, args);
    }


    /**
     * 获取指定名称的 Bean 定义。
     * 该方法由子类实现，用于获取 Bean 的定义信息。
     *
     * @param beanName 要获取定义的 Bean 的名称
     * @return 返回对应的 Bean 定义
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName);

    /**
     * 根据 Bean 定义和构造函数参数创建指定名称的 Bean 实例。
     * 该方法由子类实现，用于根据 Bean 的定义信息和构造函数参数创建 Bean 实例。
     *
     * @param beanName       要创建的 Bean 的名称
     * @param beanDefinition 包含 Bean 创建所需的定义信息
     * @param args           用于实例化 Bean 的构造函数参数，如果没有参数则为 null
     * @return 返回创建的 Bean 实例
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args);
}
