package cn.learn.beans.processor;

import cn.learn.beans.exception.BeansException;

// note: 用于在 Bean 实例化前后对 Bean 进行一些定制的处理，比如代理、AOP、依赖注入等
public interface BeanPostProcessor {

    /**
     * 在 Bean 对象执行初始化方法之前执行
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在 Bean 对象执行初始化方法之后执行
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
