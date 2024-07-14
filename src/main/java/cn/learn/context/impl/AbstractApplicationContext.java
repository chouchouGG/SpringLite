package cn.learn.context.impl;

import cn.learn.aware.ApplicationContextAwareProcessor;
import cn.learn.beans.processor.BeanFactoryPostProcessor;
import cn.learn.beans.processor.BeanPostProcessor;
import cn.learn.context.ConfigurableApplicationContext;
import cn.learn.context.eventsystem.ApplicationEventListener;
import cn.learn.context.eventsystem.event.ApplicationEvent;
import cn.learn.context.eventsystem.event.ContextClosedEvent;
import cn.learn.context.eventsystem.event.ContextRefreshedEvent;
import cn.learn.context.eventsystem.multicaster.ApplicationEventMulticaster;
import cn.learn.context.eventsystem.multicaster.SimpleApplicationEventMulticaster;
import cn.learn.core.io.loader.DefaultResourceLoader;
import cn.learn.exception.BeansException;
import cn.learn.beanfactory.ConfigurableListableBeanFactory;
import cn.learn.beanfactory.factory.DefaultListableBeanFactory;
import lombok.Getter;

import java.util.Collection;
import java.util.Map;


@Getter
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    /**
     * ApplicationContext 启动时的核心方法
     */
    @Override
    public void refresh() throws BeansException {
        // 1. 【创建并加载】： 创建 BeanFactory，并通过读取配置文件或其他方式加载 Bean 定义。
        createBeanFactoryAndLoadBeanDefinition();

        // 3. 【执行处理方法】：执行 BeanFactoryPostProcessor，这些处理器可以在 Bean 实例化之前修改 Bean 的定义。
        // 假设有一个属性值是占位符形式，比如 "${database.url}"，可用于解析占位符并替换实际值
        callBeanFactoryPostProcessors(getBeanFactory());

        // 4. 【初始化处理器】：注册 BeanPostProcessor。处理器将在 Bean 初始化的前后执行预设的处理逻辑。
        // 这里负责将所有的处理器添加到 bean 工厂的处理器列表中，具体处理器对哪些 bean 生效是由处理器自己控制。
        initBeanPostProcessors(getBeanFactory());

        // 5. 【初始化事件机制】
        initEventHandling(getBeanFactory());

        // 7. note：【单例实例化】：采取主动预加载单例Bean对象，在应用上下文加载时所有单例 Bean 都已实例化。
        getBeanFactory().preInstantiateSingletons();

        // 8. 发布容器刷新完成事件
        finishRefresh();
    }

    private void initEventHandling(ConfigurableListableBeanFactory beanFactory) {
        // 5. 初始化事件多播器
        initApplicationEventMulticaster(beanFactory);

        // 6. 注册事件监听器
        registerListeners(beanFactory);
    }

    /**
     * refresh，即创建 Bean 工厂，并通过 Bean 工厂来加载 Bean
     */
    protected abstract void createBeanFactoryAndLoadBeanDefinition() throws BeansException;

    protected abstract DefaultListableBeanFactory getBeanFactory();

    private void callBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> processors = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor processor : processors.values()) {
            processor.postProcessBeanFactory(beanFactory);
        }
    }

    private void initBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        // 添加【应用上下文感知处理器】：感知处理器是一种特殊的处理器，作为扩展其本质也是利用 BeanPostProcessors 提供的的功能
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 注册所有的自定义的 BeanPostProcessor 处理器。
        Map<String, BeanPostProcessor> processors = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor processor : processors.values()) {
            beanFactory.addBeanPostProcessor(processor);
        }
    }

    private void initApplicationEventMulticaster(ConfigurableListableBeanFactory beanFactory) {
        applicationEventMulticaster = new SimpleApplicationEventMulticaster();
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    private void registerListeners(ConfigurableListableBeanFactory beanFactory) {
        Collection<ApplicationEventListener> applicationListeners = beanFactory.getBeansOfType(ApplicationEventListener.class).values();
        for (ApplicationEventListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    /**
     * 发布一个 ContextRefreshedEvent 事件，通知相关监听器应用上下文已经刷新完毕
     */
    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        // 执行单例Bean的销毁方法
        getBeanFactory().destroySingletons();
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
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
