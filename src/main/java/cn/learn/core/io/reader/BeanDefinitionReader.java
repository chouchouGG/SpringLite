package cn.learn.core.io.reader;

import cn.learn.beans.registry.BeanDefinitionRegistry;
import cn.learn.core.io.loader.ResourceLoader;
import cn.learn.exception.BeansException;
import cn.learn.core.io.resource.Resource;

public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    /**
     * 用户直接传入配置文件的路径location
      */
    void loadBeanDefinitions(String... locations) throws BeansException;

}