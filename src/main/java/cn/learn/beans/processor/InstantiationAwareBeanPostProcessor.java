package cn.learn.beans.processor;

import cn.learn.beans.entity.BeanDefinition;
import cn.learn.exception.BeansException;

/**
 * 一个扩展接口，它扩展了 BeanPostProcessor 接口，提供了更多的钩子方法来干预 Bean 的实例化过程，{@link BeanPostProcessor} 的子接口。
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * 在 Bean 实例化之前，可以进行干预，例如返回一个代理对象，从而绕过默认的实例化过程。
     */
    default Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }


    /**
     * 在 Bean 对象实例化完成后，设置属性操作之前执行此方法，修改 Bean 的属性值（PropertyValues）
     */
    default void postProcessPropertyValues(BeanDefinition beanDef) throws BeansException {
        return;
    }

}