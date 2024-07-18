package cn.learn.beans.processor;

import cn.learn.exception.BeansException;
import cn.learn.beanfactory.ConfigurableListableBeanFactory;

/**
 * <p>BeanFactoryPostProcessor 是 Spring 框架中用来在 BeanFactory 标准初始化之后修改其内部 Bean 定义的接口。</p>
 * <p>职责：在 BeanFactory 标准初始化之后，允许对 Bean 定义进行修改。</p>
 * <p>运行时机：在所有的 Bean 定义加载完成后，但在任何 Bean 实例化之前。</p>
 * <p>适用场景：适用于需要修改 Bean 定义或注册额外的 Bean 定义的场景。</p>
 */
public interface BeanFactoryPostProcessor {

    /**
     * {@link BeanFactoryPostProcessor} 是一个 Spring 框架中的接口，在所有的 BeanDefinition 注册完成后，实例化 Bean 之前执行。
     * 通过实现这个接口，开发者可以在容器的元数据层面上进行调整。
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
