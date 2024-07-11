package cn.learn.context.eventsystem.multicaster;

import cn.learn.context.eventsystem.event.ApplicationEvent;
import cn.learn.context.eventsystem.ApplicationEventListener;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    /**
     * 触发对当前事件感兴趣的监听器的处理操作。
     * @param event 当前事件
     */
    @Override
    public void multicastEvent(ApplicationEvent event) {
        // 获取对该事件感兴趣的所有监听器
        Collection<ApplicationEventListener<ApplicationEvent>> interestedListeners = getInterestedListeners(event);

        // 遍历所有感兴趣的监听器，并调用它们的 onApplicationEvent 方法处理事件
        for (ApplicationEventListener interestedListener : interestedListeners) {
            interestedListener.onApplicationEvent(event);
        }
    }

}
