package cn.learn.context.eventsystem.event;


import cn.learn.context.ConfigurableApplicationContext;
import cn.learn.context.eventsystem.ApplicationEventListener;

/**
 * 当 <code>ApplicationContext</code> 被关闭时引发的事件。
 *
 * <p>通常是 {@link ConfigurableApplicationContext#close()} 方法的调用触发此事件。此事件表明上下文处于关闭过程中。
 *
 * <p>对该事件感兴趣的监听器可以实现 {@link ApplicationEventListener} 接口，在上下文完全关闭之前执行自定义逻辑。
 *
 */
public class ContextClosedEvent extends ApplicationContextEvent {

    public ContextClosedEvent(Object source) {
        super(source);
    }

}
