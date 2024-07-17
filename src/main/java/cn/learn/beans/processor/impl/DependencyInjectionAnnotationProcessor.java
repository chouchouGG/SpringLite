package cn.learn.beans.processor.impl;

import cn.learn.annotation.Autowired;
import cn.learn.annotation.Qualifier;
import cn.learn.annotation.Value;
import cn.learn.beanfactory.BeanFactory;
import cn.learn.beanfactory.ConfigurableListableBeanFactory;
import cn.learn.beans.entity.BeanDefinition;
import cn.learn.beans.entity.BeanReference;
import cn.learn.beans.entity.PropertyValues;
import cn.learn.beans.processor.InstantiationAwareBeanPostProcessor;
import cn.learn.exception.BeansException;
import javafx.beans.property.Property;

import java.lang.reflect.Field;
import java.util.Arrays;


public class DependencyInjectionAnnotationProcessor implements InstantiationAwareBeanPostProcessor {

    private final ConfigurableListableBeanFactory beanFactory;

    public DependencyInjectionAnnotationProcessor(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 处理 {@link Value} 注解和 {@link Autowired} 注解。
     */
    @Override
    public void postProcessPropertyValues(BeanDefinition beanDef) throws BeansException {
        PropertyValues pvs = beanDef.getPropertyValues();

        // 1. 处理注解 @Value
        Field[] declaredFields = beanDef.getBeanClass().getDeclaredFields();

        for (Field field : declaredFields) {
            String fieldName = field.getName();
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (null != valueAnnotation) {
                String placeholder = valueAnnotation.value();
                String resolvedVal = beanFactory.resolveEmbeddedValue(placeholder);
                pvs.addPropertyValue(fieldName, resolvedVal);
            }
        }

        // 2. 处理注解 @Autowired
        for (Field field : declaredFields) {
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            if (null != autowiredAnnotation) {
                String fieldName = field.getName();
                Class<?> fieldType = field.getType();
                BeanReference beanReference = null;
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                if (null != qualifierAnnotation) {
                    String dependentBeanName = qualifierAnnotation.value();
                    beanReference = new BeanReference(dependentBeanName);
                } else {
                    String[] beanNames = beanFactory.getBeanNamesOfType(fieldType);
                    if (1 == beanNames.length) {
                        beanReference = new BeanReference(beanNames[0]);
                    } else {
                        throw new BeansException(String.format("fieldType: %s注入时，发现了 %d 个类型匹配的Bean: %s", fieldType, beanNames.length, Arrays.toString(beanNames)));
                    }
                }
                pvs.addPropertyValue(fieldName, beanReference);
            }
        }

    }
}
