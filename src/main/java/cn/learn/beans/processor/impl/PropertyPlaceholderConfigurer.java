package cn.learn.beans.processor.impl;

import cn.learn.beanfactory.ConfigurableListableBeanFactory;
import cn.learn.beans.entity.BeanDefinition;
import cn.learn.beans.entity.PropertyValues;
import cn.learn.beans.processor.BeanFactoryPostProcessor;
import cn.learn.core.io.loader.DefaultResourceLoader;
import cn.learn.exception.BeansException;
import cn.learn.util.StringValueResolver;
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

    /**
     * 默认的 XXX.properties 配置文件的加载路径
     */
    private final static String DEFAULT_RESOURCE_LOADING_LOCATION = "classpath:application.properties";


    private String resourceLoadingLocation = DEFAULT_RESOURCE_LOADING_LOCATION;

    public PropertyPlaceholderConfigurer() {
    }

    public PropertyPlaceholderConfigurer(String resourceLoadingLocation) {
        this.resourceLoadingLocation = resourceLoadingLocation;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            // configProperties 以键值对形式存储对配置文件的解析结果
            Properties configProperties = loadResourceLocation();

            // 2. 依次处理Xml文件中配置的所有 BeanDefinition 的属性值
            // （如果当前的bean并不是通过Xml文件来配置的属性值，则在逻辑上这一步可以跳过，因为这是在）
            BeanDefinition[] beanDefinitions = beanFactory.getBeanDefinitions();
            for (BeanDefinition beanDefinition : beanDefinitions) {
                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (Map.Entry<String, Object> propertyValue : propertyValues.getPropertyValueEntries()) {
                    String propKey = propertyValue.getKey();
                    Object propVal = propertyValue.getValue();
                    if (propVal instanceof String) {
                        // 解析占位符
                        String configVal = resolvePlaceholder((String) propVal, configProperties);
                        // 更新 propertyValues
                        if (configVal != null) {
                            propertyValues.addPropertyValue(propKey, configVal);
                        } else {
                            throw new RuntimeException("配置文件: " + resourceLoadingLocation + "中未找到占位符对应的键: " + propKey);
                        }
                    }
                }
            }
            // 向容器中添加字符串解析器，供解析 @Value 注解使用
            beanFactory.addEmbeddedValueResolver(new PlaceholderStringValueResolver(configProperties));
        } catch (IOException e) {
            throw new BeansException("不能加载配置文件：" + resourceLoadingLocation, e);
        }
    }

    private Properties loadResourceLocation() throws IOException {
        Properties configProperties = new Properties();
        // 1. 加载属性文件
        configProperties.load(DefaultResourceLoader.loadResourceLocationAsStream(resourceLoadingLocation));
        return configProperties;
    }

    private String resolvePlaceholder(String propValue, Properties configProperties) {
        String configVal = propValue;
        // 获取占位符对应于配置文件中的实际值
        if (propValue.startsWith(DEFAULT_PLACEHOLDER_PREFIX) && propValue.endsWith(DEFAULT_PLACEHOLDER_SUFFIX)) {
            // 去掉开头和结尾的占位符符号，提取占位符中的键
            String configKey = propValue.substring(2, propValue.length() - 1);
            configVal = configProperties.getProperty(configKey);
        }
        return configVal;
    }

    /**
     * 占位符的解析器
     */
    private class PlaceholderStringValueResolver implements StringValueResolver {

        private final Properties properties;

        public PlaceholderStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        /**
         * note: <code>PropertyPlaceholderConfigurer.this</code>：这是 Java 中的语法，用于在内部类或匿名类中引用外围类的实例。
         *  <p>1. 如果仅使用 this，则引用的是内部类的实例。</p>
         *  <p>2. 使用 外围类名.this 可以引用外围类的实例。</p>
         */
        @Override
        public String resolveStringValue(String strVal) {
            return (PropertyPlaceholderConfigurer.this).resolvePlaceholder(strVal, this.properties);
        }

    }

}