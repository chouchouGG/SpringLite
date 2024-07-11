package cn.learn.context.eventsystem.event;


import cn.learn.context.ConfigurableApplicationContext;
import cn.learn.context.eventsystem.ApplicationEventListener;

/**
 * <code>ContextRefreshedEvent</code> 用于表示 <code>ApplicationContext</code> 被初始化或刷新时触发的事件。
 *
 * <p>通常是 {@link ConfigurableApplicationContext#refresh()} 方法调用触发此事件。表明应用程序上下文已准备就绪。
 *
 * <p>对该事件感兴趣的监听器可以实现 {@link ApplicationEventListener} 接口在上下文刷新后执行自定义逻辑。
 */
public class ContextRefreshedEvent extends ApplicationContextEvent {

    public ContextRefreshedEvent(Object source) {
        super(source);
    }

}
