package cn.learn.beans.singleton;

import cn.learn.beans.DisposableBean;
import cn.learn.beans.exception.BeansException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
     * 销毁方法的具体方法信息被注册到 disposableBeans 中。
     */
    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    /**
     * 从单例缓存中获取单例对象
     */
    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /**
     * 将单例对象添加到单例缓存中
     */
    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    /**
     * 将销毁方法注册到缓存中
     */
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    /**
     * 循环调用所有Bean的销毁方法
     */
    @Override
    public void destroySingletons() {
        Set<String> keySet = disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        // 按照注册时的逆序调用destory方法
        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            String beanName = (String) disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("'" + beanName + "' 的销毁方法产生异常", e);
            }
        }
    }
}
