package cn.learn.factory.support;

import cn.learn.factory.config.BeanDefinition;
import cn.learn.factory.exception.BeansException;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-05 00:59
 **/
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        Object bean;
        try {
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException e) {
            throw new BeansException("实例化bean失败: " + beanDefinition.getBeanClass().getName(), e);
        } catch (IllegalAccessException e) {
            throw new BeansException("实例化bean时的非法访问: " + beanDefinition.getBeanClass().getName(), e);
        }
        addSingleton(beanName, bean);
        return bean;
    }

}
