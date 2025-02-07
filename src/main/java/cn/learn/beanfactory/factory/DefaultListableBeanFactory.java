package cn.learn.beanfactory.factory;

import cn.learn.beans.entity.BeanDefinition;
import cn.learn.exception.BeansException;
import cn.learn.beanfactory.ConfigurableListableBeanFactory;
import cn.learn.beans.registry.BeanDefinitionRegistry;

import java.util.*;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-05 07:23
 **/
public class DefaultListableBeanFactory extends  AbstractAutowireCapableBeanFactory
        implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    protected Map<String, BeanDefinition> getBeanDefinitionMap() {
        return this.beanDefinitionMap;
    }

    // note: DefaultListableBeanFactory 拥有 BeanDefinition 的注册能力（由于实现了 BeanDefinitionRefistry 接口）
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }


    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> result = new HashMap<>();
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            Class<?> beanClass = entry.getValue().getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                result.put(beanName, (T) getBean(beanName));
            }
        }
        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public BeanDefinition[] getBeanDefinitions() {
        return beanDefinitionMap.values().toArray(new BeanDefinition[0]);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("没有命名为 '" + beanName + "' 的Bean被定义");
        }
        return beanDefinition;
    }

    /**
     * 采取主动预加载单例Bean对象，在应用上下文加载时所有单例 Bean 都已实例化。
     */
    @Override
    public void preInstantiateSingletons() throws BeansException {
        String[] beanNames = getBeanDefinitionNames();
        for (String beanName : beanNames) {
            // 只有配置为单例的bean，才预先进行实例化
            // 单例通常都是无状态的，故直接调用getBean(String)
            if (getBeanDefinition(beanName).isSingleton()) {
                getBean(beanName);
            }
        }
    }

}