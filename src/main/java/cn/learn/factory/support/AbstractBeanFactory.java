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
        // 尝试从单例缓存中获取 Bean
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }
        // 1. 获取 Bean 定义
        BeanDefinition beanDefinition = getBeanDefinition(name);
        // 2. 创建 Bean 实例
        return createBean(name, beanDefinition);
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
     * 创建指定名称的 Bean 实例。
     * 该方法由子类实现，用于根据 Bean 定义创建 Bean 实例。
     *
     * @param beanName 要创建的 Bean 的名称
     * @param beanDefinition 创建 Bean 所需的定义信息
     * @return 返回创建的 Bean 实例
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition);
}
