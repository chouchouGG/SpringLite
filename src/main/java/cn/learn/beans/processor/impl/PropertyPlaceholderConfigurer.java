package cn.learn.beans.processor.impl;

import cn.learn.beanfactory.ConfigurableListableBeanFactory;
import cn.learn.beans.entity.BeanDefinition;
import cn.learn.beans.entity.PropertyValues;
import cn.learn.beans.processor.BeanFactoryPostProcessor;
import cn.learn.core.io.loader.DefaultResourceLoader;
import cn.learn.exception.BeansException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    /**
     * 默认的占位符前缀: {@value}
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    /**
     * 默认的占位符后缀: {@value}
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String resourceLoadinglocation;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // properties 以键值对形式存储对配置文件的解析结果
        Properties properties = new Properties();

        try {
            // 1. 加载属性文件
            properties.load(DefaultResourceLoader.loadResourceLocationAsStream(resourceLoadinglocation));

            // 2. 遍历所有 BeanDefinition
            BeanDefinition[] beanDefinitions = beanFactory.getBeanDefinitions();
            for (BeanDefinition beanDefinition : beanDefinitions) {
                PropertyValues propertyValues = beanDefinition.getPropertyValues();

                for (Map.Entry<String, Object> propertyValue : propertyValues.getPropertyValueEntries()) {
                    String name = propertyValue.getKey();
                    Object value = propertyValue.getValue();

                    if (!(value instanceof String)) {
                        continue;
                    }
                    String strVal = (String) value;

                    // 3. 替换属性值中的占位符
                    if (strVal.startsWith("${") && strVal.endsWith("}")) {
                        // 去掉开头和结尾的占位符符号，提取占位符中的键
                        String propKey = strVal.substring(2, strVal.length() - 1);
                        String propVal = properties.getProperty(propKey);

                        if (propVal != null) {
                            propertyValues.addPropertyValue(name, propVal);
                        } else {
                            log.info("配置文件中未找到占位符的键: " + propKey);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new BeansException("不能加载配置文件：" + resourceLoadinglocation, e);
        }
    }

    public void setLocation(String resourceLoadinglocation) {
        this.resourceLoadinglocation = resourceLoadinglocation;
    }

}