package cn.learn.aware;

import cn.learn.exception.BeansException;
import cn.learn.beans.processor.BeanPostProcessor;
import cn.learn.context.ApplicationContext;
import lombok.AllArgsConstructor;

/**
 * 该类实现了 BeanPostProcessor，用于在 Bean 初始化之前将 ApplicationContext 注入到实现 ApplicationContextAware 接口的 Bean 中。为 Bean 扩展应用上下文的感知功能。
 */
@AllArgsConstructor
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    // 保存应用上下文的实例，供后续注入使用。
    private final ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 如果 Bean 实现了 ApplicationContextAware 接口，则调用 setApplicationContext 方法，将 applicationContext 注入到该 Bean 中。
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
