package cn.learn.context.eventsystem.multicaster;

import cn.learn.context.eventsystem.event.ApplicationEvent;
import cn.learn.context.eventsystem.ApplicationEventListener;

/**
 * 这个接口是 Spring 框架中的一个核心接口，用于管理和广播应用事件。
 * <p>这个接口的实现通常管理一个监听器集合并提供方法，用于添加和删除监听器。当事件发布时，将调用 {@code multicastEvent} 方法，然后通知所有合适的监听器。
 */
public interface ApplicationEventMulticaster {

    /**
     * 添加一个监听器，使其接收所有发布的事件。
     */
    void addApplicationListener(ApplicationEventListener<?> listener);

    /**
     * 从通知列表中移除一个监听器，使其不再接收事件。
     */
    void removeApplicationListener(ApplicationEventListener<?> listener);

    /**
     * 将给定的事件多播到所有合适的监听器。
     */
    void multicastEvent(ApplicationEvent event);

}