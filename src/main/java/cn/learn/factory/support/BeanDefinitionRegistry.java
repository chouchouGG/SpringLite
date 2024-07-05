package cn.learn.factory.support;

import cn.learn.factory.config.BeanDefinition;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-05 07:24
 **/
public interface BeanDefinitionRegistry {

    /**
     * 注册一个 Bean 定义到 Bean 工厂中
     *
     * @param beanName       要注册的 Bean 的名称
     * @param beanDefinition 要注册的 Bean 的定义
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
