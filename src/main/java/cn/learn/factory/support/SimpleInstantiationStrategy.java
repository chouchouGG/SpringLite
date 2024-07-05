package cn.learn.factory.support;

import cn.learn.factory.config.BeanDefinition;
import cn.learn.factory.exception.BeansException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-05 10:05
 **/
public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition, Constructor ctor, Object[] args) {
        Class clazz = beanDefinition.getBeanClass();
        try {
            // ctor如果为空则是无参构造函数实例化，否则就是需要带参构造函数的实例化。
            if (null != ctor) {
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            } else {
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeansException("未能实例化 [" + clazz.getName() + "]", e);
        }
    }
}
