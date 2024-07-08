package cn.learn.beans.entity;

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

    // 这两个字段是为了能在spring.xml的<bean>标签中配置'init-method'和'destory-method'两个属性
    private String initMethodName;
    private String destroyMethodName;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = (propertyValues == null) ? new PropertyValues() : propertyValues;
    }

}
