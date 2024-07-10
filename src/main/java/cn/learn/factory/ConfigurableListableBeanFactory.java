package cn.learn.factory;

import cn.learn.exception.BeansException;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    void preInstantiateSingletons() throws BeansException;

}