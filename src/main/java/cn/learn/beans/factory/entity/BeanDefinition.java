package cn.learn.beans.factory.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @program: SpringLite
 * @description: 包装注册到容器中的信息
 * @author: chouchouGG
 * @create: 2024-07-04 17:52
 **/
@Getter
@Setter
public class BeanDefinition {

    private Class beanClass;

    private PropertyValues propertyValues;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = (propertyValues == null) ? new PropertyValues() : propertyValues;
    }

}
