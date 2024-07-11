package cn.learn.context.eventsystem;

import cn.learn.context.eventsystem.event.ApplicationEvent;

import java.util.EventListener;

/**
 * 用于定义事件监听器。
 * <p>通过实现 {@link #onApplicationEvent(ApplicationEvent)} 方法处理相应事件。</p>
 * <p> <p><code>&lt;E extends ApplicationEvent&gt;</code>：定义了监听器可以处理的特定类型的事件。</p>
 *
 * @param <E> 监听的具体的事件类型
 */
public interface ApplicationEventListener<E extends ApplicationEvent> extends EventListener {

    /**
     * 处理 <code>ApplicationEvent</code> 事件
     */
    void onApplicationEvent(E event);

}
