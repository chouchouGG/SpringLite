package cn.learn.beans.factory;

import cn.learn.beans.factory.config.BeanDefinition;
import cn.learn.beans.factory.exception.BeansException;
import cn.learn.beans.factory.support.BeanDefinitionRegistry;
import cn.learn.beans.factory.support.instantiate.AbstractAutowireCapableBeanFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-05 07:23
 **/
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    protected BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("没有命名为 '" + beanName + "' 的Bean被定义");
        }
        return beanDefinition;
    }

}