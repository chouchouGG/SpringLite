package cn.learn.beanfactory;

import cn.learn.beans.entity.BeanDefinition;
import cn.learn.exception.BeansException;
import cn.learn.beanfactory.singleton.SingletonBeanRegistry;
import cn.learn.beans.processor.BeanPostProcessor;
import cn.learn.util.StringValueResolver;

/**
 * @program: SpringLite
 * @description: 负责配置和管理 Bean 的定义，并定义了两个常用的 Bean 作用域（singleton 和 prototype）
 * @author: chouchouGG
 * @create: 2024-07-05 23:34
 **/
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 获取指定名称的 BeanDefinition。
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 添加一个 BeanPostProcessor 实例。
     * BeanPostProcessor 用于在 Bean 的初始化前后进行处理。
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    String resolveEmbeddedValue(String value);

    void addEmbeddedValueResolver(StringValueResolver valueResolver);
}