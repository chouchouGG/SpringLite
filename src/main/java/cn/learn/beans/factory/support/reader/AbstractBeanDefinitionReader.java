package cn.learn.beans.factory.support.reader;

import cn.learn.beans.factory.support.registry.BeanDefinitionRegistry;
import cn.learn.core.io.ResourceLoader;
import cn.learn.core.io.impl.DefaultResourceLoader;
import lombok.Getter;

@Getter
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    protected final BeanDefinitionRegistry beanRegistry;

    protected ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    // 内部提供了默认的资源加载器 DefaultResourceLoader
    public AbstractBeanDefinitionReader(BeanDefinitionRegistry beanRegistry, ResourceLoader resourceLoader) {
        this.beanRegistry = beanRegistry;
        this.resourceLoader = resourceLoader;
    }

}