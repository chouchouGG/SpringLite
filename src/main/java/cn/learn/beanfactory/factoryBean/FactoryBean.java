package cn.learn.beanfactory.factoryBean;

/**
 * FactoryBean 接口定义了一个 Spring 容器使用的工厂接口。如果一个 bean 实现了这个接口，Spring 容器不会直接将这个 bean 实例作为一个暴露的 bean，而是将其作为一个工厂来创建另一个对象。
 * @param <T>
 */
public interface FactoryBean<T> {

    /**
     * 返回由该工厂Bean管理的对象的一个实例。该实例可以是共享的（单例）也可以是独立的（原型）。
     */
    T getObject() throws Exception;

    /**
     * 返回由该工厂创建的对象的类型。
     */
    Class<?> getObjectType();

    /**
     * 返回该工厂管理的对象是否是单例。
     */
    boolean isSingleton();

}