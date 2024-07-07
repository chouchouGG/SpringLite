package cn.learn.common;


import cn.learn.beans.entity.BeanDefinition;
import cn.learn.beans.entity.PropertyValue;
import cn.learn.beans.entity.PropertyValues;
import cn.learn.beans.exception.BeansException;
import cn.learn.beans.factory.ConfigurableListableBeanFactory;
import cn.learn.beans.processor.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("company", "MyBeanFactoryPostProcessor中修改为：Shopee"));
    }

}
