package cn.learn.beanfactory;

import cn.learn.exception.BeansException;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-05 23:32
 **/
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 执行 BeanPostProcessors 接口实现类的前置处理方法
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 执行 BeanPostProcessors 接口实现类的后置处理方法
     */
    Object applyBeanPostProcessorsAfterInitialization(Object bean, String beanName) throws BeansException;

}
