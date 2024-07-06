package cn.learn.beans.factory.support;

import cn.learn.beans.factory.config.BeanDefinition;
import cn.learn.beans.factory.exception.BeansException;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

}