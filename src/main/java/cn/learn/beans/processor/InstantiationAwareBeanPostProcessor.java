package cn.learn.beans.processor;

import cn.learn.exception.BeansException;

/**
 * 定义 InstantiationAwareBeanPostProcessor 的目的是提供更细粒度的控制，{@link BeanPostProcessor} 的子接口。
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * 在 Bean 实例化之前，可以进行干预，例如返回一个代理对象，从而绕过默认的实例化过程。
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

}