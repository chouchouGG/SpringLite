package cn.learn.beans.processor;

import cn.learn.exception.BeansException;

// note: 用于在 Bean 实例化前后对 Bean 进行一些定制的处理，比如代理、AOP、依赖注入等
public interface BeanPostProcessor {

    /**
     * 在 Bean 对象执行初始化方法之前执行
     * @return  默认返回传入的 <code>bean</code>，表示继续后续处理器的处理流程。特殊情况下，可以通过重写方法并返回 <code>null</code> 来终止处理器的处理流程。
     */
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 在 Bean 对象执行初始化方法之后执行
     * @return 默认返回传入的 <code>bean</code>，表示继续后续处理器的处理流程。特殊情况下，可以通过重写方法并返回 <code>null</code> 来终止处理器的处理流程。
     */
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
