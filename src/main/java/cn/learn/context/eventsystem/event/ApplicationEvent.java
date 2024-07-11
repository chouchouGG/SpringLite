package cn.learn.context.eventsystem.event;


import java.util.EventObject;

/**
 * ApplicationEvent 用于定义应用事件。所有的应用事件都需要继承自这个类，这是 Spring 框架核心事件机制的一部分，提供了一种方式让 Bean 之间通信而不会紧耦合。
 * <p>
 *     EventObject 类是 Java 标准库中的一个类，它是所有事件状态对象的根类。事件状态对象封装了事件源和与事件相关的其他信息。
 * </p>
 */
public abstract class ApplicationEvent extends EventObject {

    public ApplicationEvent(Object source) {
        super(source);
    }

}