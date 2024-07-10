package cn.learn.core.io.reader;

import cn.learn.beans.registry.BeanDefinitionRegistry;
import cn.learn.core.io.loader.ResourceLoader;
import cn.learn.core.io.loader.DefaultResourceLoader;
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