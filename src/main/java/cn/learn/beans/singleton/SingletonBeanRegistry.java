package cn.learn.beans.singleton;

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

    void registerSingleton(String beanName, Object singletonObject);

}
