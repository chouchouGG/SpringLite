package cn.learn.beanfactory.singleton;

import cn.learn.beans.ObjectFactory;
import cn.learn.beans.support.DisposableBean;
import cn.learn.exception.BeansException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-04 19:33
 **/
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * note: 思考这里为什么使用HashMap，而不是用ConcurrentHashMap？
     * <p>答：singletonObjects 只会在单线程上下文中访问（例如在 BeanFactory 初始化过程中写入，之后使用都为读），
     * 因此不需要使用 ConcurrentHashMap。如果涉及多线程访问，则应考虑使用 ConcurrentHashMap 以确保线程安全。</p>
     */
    /**
     * <h2>一级缓存，存放完全初始化的单例对象。</h2>
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    /**
     * <h2>二级缓存：存放提前暴露且暂未完全实例化的单例对象。</h2>
     */
    protected final Map<String, Object> earlySingletonObjects = new HashMap<>();

    /**
     * <h2>三级缓存：存放工厂对象、代理对象。</h2>
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();

    /**
     * 存储销毁方法的具体方法信息
     */
    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    /**
     * NULL_OBJECT 是一个内部标记对象，用于表示单例对象缓存中的 null 值，因为 ConcurrentHashMap 不支持存储 null 值。
     */
    protected static final Object NULL_OBJECT = new Object();


    /**
     * 一层层处理不同时期的单例对象，直至拿到有效的对象
     */
    @Override
    public Object getSingleton(String beanName) {
        Object singletonObject = singletonObjects.get(beanName);
        if (null == singletonObject) {
            singletonObject = earlySingletonObjects.get(beanName);
            if (null == singletonObject) {
                ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                if (null != singletonFactory) {
                    singletonObject = singletonFactory.getObject(); // 返回为空，则证明不需要Aop代理
                    earlySingletonObjects.put(beanName, singletonObject); // 放入到二级缓存
                    singletonFactories.remove(beanName); // 从三级缓存删除
                }
            }
        }
        return singletonObject;
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

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        // 添加到一级
        singletonObjects.put(beanName, singletonObject);
        // 从二级移除
        earlySingletonObjects.remove(beanName);
        // 从三级移除
        singletonFactories.remove(beanName);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory){
        if (!this.singletonObjects.containsKey(beanName)) { // 如果一级缓存中已经存在，则直接返回，不做任何处理，确保一个单例 Bean 只有一个实例被创建和使用。
            this.singletonFactories.put(beanName, singletonFactory); // 三级缓存（singletonFactories）用于存储工厂对象。
            this.earlySingletonObjects.remove(beanName);
        }
    }

    /**
     * 将销毁方法注册到缓存中
     */
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }
}
