package cn.learn.factory.impl;

import cn.learn.beans.factoryBean.FactoryBean;
import cn.learn.beans.factoryBean.FactoryBeanRegistrySupport;
import cn.learn.beans.entity.BeanDefinition;
import cn.learn.factory.ConfigurableBeanFactory;
import cn.learn.beans.processor.BeanPostProcessor;
import cn.learn.util.ClassUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-04 23:45
 **/
@Getter
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

     // 处理器列表
     private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

     // 默认的类加载器
     private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

//    @Override
//    public Object getBean(String name) {
//        return doGetBean(name, null);
//    }

    @Override
    public Object getBean(String name, Object... args) {
         return doGetBean(name, args);
    }

    /**
     * 根据 Bean 的名称和类型获取 Bean 实例。
     *
     * @param name         Bean 的名称
     * @param requiredType 所需的 Bean 类型
     * @param <T>          Bean 的类型
     * @return 与给定名称对应的 Bean 实例，并转换为指定的类型
     */
    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return (T) getBean(name);
    }

    protected <T> T doGetBean(final String name, final Object[] args) {
        // 尝试从单例缓存中获取 Bean
        Object sharedInstance = getSingleton(name);
        // 如果是代理bean，需要调用代理bean的getObject，获取实际的对象
        if (sharedInstance != null) {
            // 如果是 FactoryBean，则需要调用 FactoryBean#getObject
            return (T) getObjectIfFactoryBean(sharedInstance, name);
        }
        // 创建 Bean：实例化 + 初始化
        Object bean = createBean(name, getBeanDefinition(name), args);
        return (T) getObjectIfFactoryBean(bean, name);
    }

    private Object getObjectIfFactoryBean(Object beanInstance, String beanName) {
        // 如果不是 FactoryBean 类型，直接返回
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        // 从缓存中获取
        Object object = getCachedObjectFromFactoryBean(beanName);
        // 缓存未命中
        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }
        return object;
    }

    /**
     * 根据 Bean 定义和构造函数参数，创建指定名称的 Bean 实例。
     *
     * @param beanName       要创建的 Bean 的名称
     * @param beanDefinition 包含 Bean 创建所需的定义信息
     * @param args           用于实例化 Bean 的构造函数参数，如果没有参数则为 null
     * @return 返回创建的 Bean 实例
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args);

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

}
