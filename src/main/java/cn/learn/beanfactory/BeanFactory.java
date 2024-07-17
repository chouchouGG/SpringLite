package cn.learn.beanfactory;

import cn.learn.exception.BeansException;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-04 17:56
 **/
public interface BeanFactory {

    Object getBean(String name, Object... args);

    <T> T getBean(String name, Class<T> returnType);

//    <T> T getBean(Class<T> requiredType) throws BeansException;
}
