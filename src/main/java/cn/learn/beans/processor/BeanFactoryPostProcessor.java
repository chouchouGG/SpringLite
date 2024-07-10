package cn.learn.beans.processor;

import cn.learn.exception.BeansException;
import cn.learn.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {

    /**
     * 在所有的 BeanDefinition 注册完成后，实例化 Bean 对象之前，提供修改 BeanDefinition 属性的机制
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
