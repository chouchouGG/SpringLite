package cn.learn.beans.processor;

import cn.learn.exception.BeansException;
import cn.learn.beanfactory.ConfigurableListableBeanFactory;

/**
 * BeanFactoryPostProcessor 是 Spring 框架中的一个扩展接口，用于在 Spring 容器的标准初始化过程之后、Bean 实例化之前执行自定义的操作。
 */
public interface BeanFactoryPostProcessor {

    /**
     * {@link BeanFactoryPostProcessor} 是一个 Spring 框架中的接口，在所有的 BeanDefinition 注册完成后，实例化 Bean 之前执行。
     * 通过实现这个接口，开发者可以在容器的元数据层面上进行调整。
     * <p>常见的实现和使用场景包括：</p>
     * <ol>1. 属性占位符配置：<p>用于在 Bean 实例化之前解析占位符属性。它会读取外部配置文件（如 `application.properties`），并将占位符替换为实际的值。</p></ol>
     *
     * <ol>2.条件性 Bean 注册<p>可以根据特定条件动态注册或修改 Bean 定义。例如，可以在运行时根据某些环境参数或配置，决定是否注册某个 Bean，或修改现有 Bean 的属性。</p></ol>
     *
     * <ol>3. 日志和监控：<p>可以在 Bean 实例化之前记录或调整 Bean 定义，便于调试和监控。通过在此阶段插入日志记录，开发者可以监控 Bean 定义的修改情况，或对 Bean 定义进行预处理以便更好地管理应用程序的行为。</p></ol>
     *
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
