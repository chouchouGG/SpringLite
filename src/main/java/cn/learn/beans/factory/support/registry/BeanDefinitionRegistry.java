package cn.learn.beans.factory.support.registry;

import cn.learn.beans.entity.BeanDefinition;

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

    /**
     * 判断是否包含指定名称的BeanDefinition
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 返回注册表中所有的Bean名称
     */
    String[] getBeanDefinitionNames();
}
