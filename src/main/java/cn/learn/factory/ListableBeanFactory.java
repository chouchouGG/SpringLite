package cn.learn.factory;

import cn.learn.exception.BeansException;

import java.util.Map;


public interface ListableBeanFactory extends BeanFactory {

    /**
     * 返回与类型 type 匹配的 Bean 实例
     *
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * Return the names of all beans defined in this registry.
     * <p>
     * 返回注册表中所有的Bean名称
     */
    String[] getBeanDefinitionNames();

}
