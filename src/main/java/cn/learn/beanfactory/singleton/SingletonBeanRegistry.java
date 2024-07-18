package cn.learn.beanfactory.singleton;

/**
 * @program: SpringLite
 * @description: 单例注册接口
 * @author: chouchouGG
 * @create: 2024-07-04 19:29
 **/
public interface SingletonBeanRegistry {

    /**
     * 获取单例对象
     */
    Object getSingleton(String beanName);

    /**
     * 销毁单例对象
     */
    void destroySingletons();

    /**
     * 注册单例对象：将完整的单例对象添加到一级缓存中
     */
    void registerSingleton(String beanName, Object singletonObject);

}
