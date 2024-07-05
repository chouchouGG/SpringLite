package cn.learn.factory.support;

import cn.learn.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-04 19:33
 **/
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * note: 思考这里为什么使用HashMap，而不是用ConcurrentHashMap？
     * <p>
     * 答：singletonObjects 只会在单线程上下文中访问（例如在 BeanFactory 初始化过程中），因此不需要使用 ConcurrentHashMap。如果涉及多线程访问，则应考虑使用 ConcurrentHashMap 以确保线程安全。
     * </p>
     *
     */
    private final Map<String, Object> singletonObjects = new HashMap<>();


    /**
     * 从单例缓存中获取单例对象
     *
     * @param beanName 要检索的 Bean 的名称
     * @return 单例对象，如果没有则返回 null
     */
    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /**
     * 将单例对象添加到单例缓存中
     *
     * @param beanName Bean 的名称
     * @param singletonObject 要添加的单例对象
     */
    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

}
