package cn.learn.context.eventsystem.event;

import cn.learn.context.ApplicationContext;

/**
 * 事件基类，用于描述与 ApplicationContext 相关的事件。所有与 ApplicationContext 相关的事件都需要继承自这个类。
 */
public class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * getSource 方法来自于 EventObject 类，返回事件最初发生的对象。在这个上下文中，getSource 返回的是 ApplicationContext 实例。
     */
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }

}
