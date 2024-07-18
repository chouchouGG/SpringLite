package cn.learn.beans;

import cn.learn.exception.BeansException;

public interface ObjectFactory<T> {

    T getObject() throws BeansException;

}