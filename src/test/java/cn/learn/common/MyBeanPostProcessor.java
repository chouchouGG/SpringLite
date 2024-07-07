package cn.learn.common;


import cn.learn.bean.UserService;
import cn.learn.beans.exception.BeansException;
import cn.learn.beans.processor.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setLocation("MyBeanPostProcessor中修改为【重庆】");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 暂时不做任何处理，直接返回
        return bean;
    }

}
