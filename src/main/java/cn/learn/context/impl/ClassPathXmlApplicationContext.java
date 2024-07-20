package cn.learn.context.impl;

import cn.learn.exception.BeansException;

/**
 * <h1>ClassPathXmlApplicationContext：</h1>
 * <p>从类路径下的一个或多个xml配置文件中加载上下文定义，适用于xml配置的方式。</p>
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private final String[] configResourcesLoadingLocations;

    /**
     * 从 XML 中加载 BeanDefinition，并刷新上下文
     *
     * @param configResourcesLoadingLocations 配置文件资源的加载地址（路径/url）
     */
    public ClassPathXmlApplicationContext(String... configResourcesLoadingLocations) throws BeansException {
        this.configResourcesLoadingLocations = configResourcesLoadingLocations;
        refresh();
    }

    @Override
    protected String[] getConfigLoadingLocations() {
        return configResourcesLoadingLocations;
    }
}