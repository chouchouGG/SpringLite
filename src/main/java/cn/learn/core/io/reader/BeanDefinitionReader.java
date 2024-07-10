package cn.learn.core.io.reader;

import cn.learn.exception.BeansException;
import cn.learn.core.io.resource.Resource;

public interface BeanDefinitionReader {

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    /**
     * 用户直接传入配置文件的路径location
      */
    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String... locations) throws BeansException;

}