package cn.learn.context.impl;

import cn.learn.beans.exception.BeansException;
import cn.learn.beans.factory.ConfigurableListableBeanFactory;
import cn.learn.beans.factory.impl.DefaultListableBeanFactory;
import lombok.Getter;

@Getter
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        beanFactory = new DefaultListableBeanFactory();
        loadBeanDefinitions();
    }

    /**
     * 读取Bean对象的配置文件并解析，完成Bean的加载（注册）
     */
    protected abstract void loadBeanDefinitions();

}