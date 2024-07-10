package cn.learn.beans.aware;


import cn.learn.exception.BeansException;
import cn.learn.factory.BeanFactory;

/**
 * 实现此接口的Bean，能感知到其所属的 BeanFactory
 */
public interface BeanFactoryAware extends Aware {

   void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
