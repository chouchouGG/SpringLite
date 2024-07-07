package cn.learn.beans.factory.impl;

import cn.learn.beans.entity.BeanDefinition;
import cn.learn.beans.exception.BeansException;
import cn.learn.beans.factory.ConfigurableBeanFactory;
import cn.learn.beans.singleton.DefaultSingletonBeanRegistry;
import cn.learn.beans.processor.BeanPostProcessor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-04 23:45
 **/
@Getter
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    // BeanPostProcessors 在 createBean 中应用
     private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    @Override
    public Object getBean(String name) {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) {
         return doGetBean(name, args);
    }

    /**
     * 根据 Bean 的名称和类型获取 Bean 实例。
     *
     * @param name         Bean 的名称
     * @param requiredType 所需的 Bean 类型
     * @param <T>          Bean 的类型
     * @return 与给定名称对应的 Bean 实例，并转换为指定的类型
     */
    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return (T) getBean(name);
    }

    protected <T> T doGetBean(final String name, final Object[] args) {
        // 尝试从单例缓存中获取 Bean
        Object bean = getSingleton(name);
        if (bean != null) {
            return (T) bean;
        }
        // 1. 获取 Bean 定义
        BeanDefinition beanDefinition = getBeanDefinition(name);
        // 2. 创建 Bean 实例
        return (T) createBean(name, beanDefinition, args);
    }


    /**
     * 根据 Bean 定义和构造函数参数，创建指定名称的 Bean 实例。
     *
     * @param beanName       要创建的 Bean 的名称
     * @param beanDefinition 包含 Bean 创建所需的定义信息
     * @param args           用于实例化 Bean 的构造函数参数，如果没有参数则为 null
     * @return 返回创建的 Bean 实例
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args);

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

}
