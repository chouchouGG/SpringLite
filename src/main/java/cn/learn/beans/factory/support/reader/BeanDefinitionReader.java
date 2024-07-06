package cn.learn.beans.factory.support.reader;

import cn.learn.beans.factory.exception.BeansException;
import cn.learn.core.io.Resource;

public interface BeanDefinitionReader {

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    /**
     * 用户直接传入配置文件的路径location
      */
    void loadBeanDefinitions(String location) throws BeansException;

}