package cn.learn.context.impl;

import cn.learn.exception.BeansException;


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