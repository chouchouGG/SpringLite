package cn.learn.beanfactory.factory;

import cn.learn.beanfactory.ListableBeanFactory;
import cn.learn.beanfactory.factoryBean.FactoryBean;
import cn.learn.beanfactory.factoryBean.FactoryBeanRegistrySupport;
import cn.learn.beans.entity.BeanDefinition;
import cn.learn.beanfactory.ConfigurableBeanFactory;
import cn.learn.beans.entity.BeanReference;
import cn.learn.beans.processor.BeanPostProcessor;
import cn.learn.exception.BeansException;
import cn.learn.util.ClassUtils;
import cn.learn.util.StringValueResolver;
import lombok.Getter;

import java.util.*;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-04 23:45
 **/
@Getter
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory, ListableBeanFactory {

     private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

     private final ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

     private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();


    /**
     * 根据 Bean 的名称和类型获取 Bean 实例。
     *
     * @param name         Bean 的名称
     * @param returnType 所需的 Bean 类型
     * @param <T>          Bean 的类型
     * @return 与给定名称对应的 Bean 实例，并转换为指定的类型
     */
    @Override
    public <T> T getBean(String name, Class<T> returnType) {
        return (T) getBean(name);
    }


    /**
     * 带参的获取Bean方法
     * @return 返回bean对象
     */
    @Override
    public Object getBean(String name, Object... args) {
        return doGetBean(name, args);
    }


    protected <T> T doGetBean(final String name, final Object[] args) {
        // 1. 依次从三层缓存中尝试获取 Bean
        Object beanInstanceOrFactoryBean = getSingleton(name);

        // 2. 【单例缓存未命中】：创建 Bean
        if (beanInstanceOrFactoryBean == null) {
            beanInstanceOrFactoryBean = createBean(name, getBeanDefinition(name), args);
        }

        // 3. FactoryBean的检查
        // bean 有可能是 FactoryBean类型（即：代理bean），则需要调用代理bean的getObject，获取实际的对象
        return (T) checkFactoryBeanAndGetObject(beanInstanceOrFactoryBean , name);
    }


    private Object checkFactoryBeanAndGetObject(Object beanInstanceOrFactoryBean, String beanName) {
        // 如果不是 FactoryBean 类型，直接返回
        if (!(beanInstanceOrFactoryBean instanceof FactoryBean)) {
            return beanInstanceOrFactoryBean;
        } else {
            // 从 FactoryBeanCache 缓存中获取 Bean 对象
            Object beanInstance = getObjectFromFactoryBeanCache(beanName);

            // 缓存未命中
            if (beanInstance == null) {
                beanInstance = getObjectFromFactoryBean((FactoryBean) beanInstanceOrFactoryBean, beanName);
            }

            return beanInstance;
        }
    }

    @Override
    public <T> String[] getBeanNamesOfType(Class<T> requiredType) {
        ArrayList<String> beanNames = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry : getBeanDefinitionMap().entrySet()) {
            String beanName = entry.getKey();
            Class<?> beanClass = entry.getValue().getBeanClass();
            // 如果需要的类型是当前类的父类
            if (requiredType.isAssignableFrom(beanClass)) {
                beanNames.add(beanName);
            }
        }
        return beanNames.toArray(new String[0]);
    }


    protected abstract Map<String, BeanDefinition> getBeanDefinitionMap();


    /**
     * 根据 Bean 定义和构造函数参数，创建指定名称的 Bean 实例。
     *
     * @param beanName       Bean 的名称
     * @param beanDefinition Bean 的定义
     * @param args           用于实例化 Bean 的构造函数参数，如果没有参数则为 null
     * @return 返回创建的 Bean 实例
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args);

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
        }
        return result;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }
}
