package cn.learn.beans.factory.singleton;

/**
 * @program: SpringLite
 * @description: 单例注册接口
 * @author: chouchouGG
 * @create: 2024-07-04 19:29
 **/
public interface SingletonBeanRegistry {

    /**
     * 获取单例对象
     * @param beanName Bean对象名称
     * @return Bean对象
     */
    Object getSingleton(String beanName);


}
