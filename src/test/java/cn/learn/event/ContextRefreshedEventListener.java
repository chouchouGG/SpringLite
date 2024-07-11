package cn.learn.event;


import cn.learn.context.eventsystem.ApplicationEventListener;
import cn.learn.context.eventsystem.event.ContextRefreshedEvent;

public class ContextRefreshedEventListener implements ApplicationEventListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("刷新事件：" + this.getClass().getName());
    }

}
