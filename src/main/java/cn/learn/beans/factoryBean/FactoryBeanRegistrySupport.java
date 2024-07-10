package cn.learn.beans.factoryBean;

import cn.learn.beans.exception.BeansException;
import cn.learn.beans.singleton.DefaultSingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    /**
     * 缓存由 FactoryBeans 创建的单例对象，(ConcurrentHashMap 不支持存储 null 值)
     */
    private final Map<String, Object> factoryBeanCache = new ConcurrentHashMap<>();

    /**
     * 这个方法用于从缓存 factoryBeanCache 中，获取指定名称的 FactoryBean 创建的对象。
     */
    protected Object getCachedObjectFromFactoryBean(String beanName) {
        Object object = this.factoryBeanCache.get(beanName);
        return (object == NULL_OBJECT) ? null : object;
    }

    /**
     * 根据 FactoryBean 的类型（单例或原型）来获取对象：
     */
    protected Object getObjectFromFactoryBean(FactoryBean<?> factoryBean, String beanName) {
        if (factoryBean.isSingleton()) {
            return getSingletonObject(factoryBean, beanName);
        } else {
            return createObjectFromFactoryBean(factoryBean, beanName);
        }
    }

    private Object getSingletonObject(FactoryBean<?> factoryBean, String beanName) {
        Object object = this.factoryBeanCache.get(beanName);
        // 只有第一次获取单例bean才进入if
        if (object == null) {
            object = createObjectFromFactoryBean(factoryBean, beanName);
            Object factoryBean1 = (object == null) ? NULL_OBJECT: object;
            this.factoryBeanCache.put(beanName, factoryBean1);
        }
        return (object == NULL_OBJECT) ? null : object;
    }

    private Object createObjectFromFactoryBean(FactoryBean factoryBean, String beanName){
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean 抛出异常在对象 [" + beanName + "] 的创建过程中", e);
        }
    }

}