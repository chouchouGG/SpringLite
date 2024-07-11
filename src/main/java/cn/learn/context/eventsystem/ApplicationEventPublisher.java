package cn.learn.context.eventsystem;

import cn.learn.context.eventsystem.event.ApplicationEvent;
import cn.learn.context.impl.AbstractApplicationContext;

/**
 * 包装了事件多播器的多播功能，实现 <code>ApplicationEventPublisher</code> 接口的 {@link AbstractApplicationContext}，
 * 保证了 ApplicationContext 对外提供发布事件的功能，而无需关系其内部的事件多播器 {@link AbstractApplicationContext#getApplicationEventMulticaster()}}。
 */
public interface ApplicationEventPublisher {

    /**
     * 发布特定的事件到所有感兴趣的监听器，并调用其处理事件操作。
     *
     * @param event 待发布的事件
     */
    void publishEvent(ApplicationEvent event);

}
