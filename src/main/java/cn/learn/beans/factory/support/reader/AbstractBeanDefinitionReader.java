package cn.learn.beans.factory.support.reader;

import cn.learn.beans.factory.support.registry.BeanDefinitionRegistry;
import cn.learn.core.io.ResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    protected final BeanDefinitionRegistry beanRegistry;

    protected final ResourceLoader resourceLoader;

    // 内部提供了默认的资源加载器 DefaultResourceLoader
    public AbstractBeanDefinitionReader(BeanDefinitionRegistry beanRegistry, ResourceLoader resourceLoader) {
        this.beanRegistry = beanRegistry;
        this.resourceLoader = resourceLoader;
    }

}