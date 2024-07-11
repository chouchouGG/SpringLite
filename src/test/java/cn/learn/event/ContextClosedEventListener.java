package cn.learn.event;


import cn.learn.context.eventsystem.ApplicationEventListener;
import cn.learn.context.eventsystem.event.ContextClosedEvent;

public class ContextClosedEventListener implements ApplicationEventListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("关闭事件：" + this.getClass().getName());
    }

}
