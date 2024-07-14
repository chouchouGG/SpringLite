package cn.learn.context.impl;

import cn.learn.core.io.reader.XmlBeanDefinitionReader;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    @Override
    protected void loadBeanDefinitions() {
        // 创建默认的 XmlBeanDefinitionReader
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(super.getBeanFactory(), this);
        String[] configLocations = getConfigLoadingLocations();
        if (null != configLocations) {
            // 程序控制流转到资源加载器，完成配置文件的加载、解析
            reader.loadBeanDefinitions(configLocations);
        }
    }

    /**
     * 由子类将用户传入的配置文件地址转为可以识别的地址，当前就是去除掉地址路径中伪的'classpath:'
     */
    protected abstract String[] getConfigLoadingLocations();

}