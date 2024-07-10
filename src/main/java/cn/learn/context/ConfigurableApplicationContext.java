package cn.learn.context;

import cn.learn.exception.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * ApplicationContext 启动期时调用，可以用于在配置更改的情况下，刷新应用上下文，重新加载所有bean和配置。
     * 步骤:
     * <p>1. 创建BeanFactory，并加载bean定义的xml配置文件。</p>
     * <p>2. 在任何bean实例化之前，执行BeanFactoryPostProcessors来修改BeanDefinition。</p>
     * <p>3. 注册BeanPostProcessors以自定义bean创建，例如代理或依赖注入。</p>
     * <p>4. 预实例化单例bean，以确保在应用程序上下文完全初始化之前创建它们。</p>
     */
    void refresh() throws BeansException;

    /**
     * 将销毁方法注册为钩子函数，在虚拟机关闭时，自动调用。
     */
    void registerShutdownHook();

    /**
     * 主动调用所有Bean的销毁方法
     */
    void close();

}
