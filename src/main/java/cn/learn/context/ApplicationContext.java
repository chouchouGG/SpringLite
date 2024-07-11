package cn.learn.context;

import cn.learn.context.eventsystem.ApplicationEventPublisher;
import cn.learn.factory.BeanFactory;

public interface ApplicationContext extends BeanFactory, ApplicationEventPublisher {

}
