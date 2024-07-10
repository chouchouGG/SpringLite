package cn.learn.beans.registry;

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
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 判断是否包含指定名称的BeanDefinition
     */
    boolean containsBeanDefinition(String beanName);

}
