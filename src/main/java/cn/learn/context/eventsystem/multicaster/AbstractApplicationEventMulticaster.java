package cn.learn.context.eventsystem.multicaster;

import cn.learn.context.eventsystem.event.ApplicationEvent;
import cn.learn.context.eventsystem.ApplicationEventListener;
import cn.learn.exception.BeansException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-11 08:40
 **/
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster {

    public final Set<ApplicationEventListener<ApplicationEvent>> subscribedListeners = new LinkedHashSet<>();

    @Override
    public void addApplicationListener(ApplicationEventListener<?> listener) {
        ApplicationEventListener<ApplicationEvent> applicationListener = (ApplicationEventListener<ApplicationEvent>) listener;
        subscribedListeners.add(applicationListener);
    }

    @Override
    public void removeApplicationListener(ApplicationEventListener<?> listener) {
        subscribedListeners.remove(listener);
    }

    /**
     * <p>返回一个对参数 <code>event</code> 感兴趣的监听器列表。</p>
     * <p>与当前事件 <code>event</code> 无关的监听器会被排除。</p>
     */
    protected Collection<ApplicationEventListener<ApplicationEvent>> getInterestedListeners(ApplicationEvent event) {
        LinkedList<ApplicationEventListener<ApplicationEvent>> listenersList = new LinkedList<>();
        for (ApplicationEventListener<ApplicationEvent> listener : subscribedListeners) {
            if (isIntrestedListener(listener, event)) {
                listenersList.add(listener);
            }
        }
        return listenersList;
    }

    /**
     * 判断监听器是否对该事件感兴趣
     *
     * @return 如果监听器对事件感兴趣，返回 true；否则返回 false
     */
    protected boolean isIntrestedListener(ApplicationEventListener<ApplicationEvent> listener, ApplicationEvent event) {
        // 1. 获取监听器的类
        Class<? extends ApplicationEventListener> listenerClass = listener.getClass();

        // 【过程调用示例】：对于 exampleEventListener implements ApplicationListener<CustomEvent>

        // 2. 获取监听器实现的第一个接口：ApplicationListener<CustomEvent>
        // 2.1 getGenericInterfaces() 返回 [ApplicationListener<CustomEvent>]
        // 2.2 getGenericInterfaces()[0] 返回 ApplicationListener<CustomEvent>
        Type genericInterface = listenerClass.getGenericInterfaces()[0];

        // 3. 获取泛型参数的实际类型：CustomEvent
        // 3.1 ApplicationListener<CustomEvent> 是一个带有泛型参数的类型，所以需要先强制转换为 ParameterizedType。
        // 3.2 调用 getActualTypeArguments() 返回 [CustomEvent]
        // 3.3 调用 getActualTypeArguments()[0] 返回 CustomEvent
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];

        // 4. 获取泛型参数的全限定名：返回 com.example.CustomEvent（假设 CustomEvent 在 com.example 包中）。
        String className = actualTypeArgument.getTypeName();

        Class<?> listenEvent = null;
        try {
            listenEvent = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("事件类名无效: " + className);
        }

        // 判定此 listenEvent 对象所表示的类或接口与指定的 event.getClass() 参数所表示的类或接口是否相同，或是否是其超类或超接口。
        return listenEvent.isAssignableFrom(event.getClass());
    }

}
