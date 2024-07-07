package cn.learn.context.impl;

import cn.learn.beans.exception.BeansException;
import cn.learn.beans.factory.ConfigurableListableBeanFactory;
import cn.learn.beans.processor.BeanFactoryPostProcessor;
import cn.learn.beans.processor.BeanPostProcessor;
import cn.learn.context.ConfigurableApplicationContext;
import cn.learn.core.io.impl.DefaultResourceLoader;

import java.util.Map;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-07 10:15
 **/
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    /**
     * ApplicationContext 启动时的核心方法
     */
    @Override
    public void refresh() throws BeansException {
        // 1. 【创建并加载】：BeanFactory，并加载 BeanDefinition
        // 创建 BeanFactory，并通过读取配置文件或其他方式加载 Bean 定义。
        refreshBeanFactory();

        // 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 2. 【执行处理方法】：在所有 BeanDefinition 注册之后立即调用方法执行。
        // 执行 BeanFactoryPostProcessor，这些处理器可以在 Bean 实例化之前修改 Bean 的定义。
        // 假设有一个属性值是占位符形式，比如 "${database.url}"，可用于解析占位符并替换实际值
        invokeBeanFactoryPostProcessors(beanFactory);

        // 3. 【注册处理器】：用于在 Bean 实例化前后对 Bean 进行一些定制的处理，比如代理、AOP、依赖注入等
        // 注册 BeanPostProcessor，这些处理器将在 Bean 实例化的前后执行定制的处理逻辑。
        registerBeanPostProcessors(beanFactory);

        // 4. 【单例实例化】：提前实例化单例Bean对象
        // 提前实例化所有单例 Bean，这样可以在应用上下文刷新时确保所有单例 Bean 都已实例化。
        beanFactory.preInstantiateSingletons();
    }

    /**
     * refresh，即创建 Bean 工厂，并通过 Bean 工厂来加载 Bean
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> processors = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor processor : processors.values()) {
            processor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> processors = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor processor : processors.values()) {
            beanFactory.addBeanPostProcessor(processor);
        }
    }

    // note：以下全是包装
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

}
