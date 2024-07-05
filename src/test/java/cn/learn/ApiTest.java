package cn.learn;

import cn.learn.bean.UserService;
import cn.learn.factory.config.BeanDefinition;
import cn.learn.factory.support.DefaultListableBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


@Slf4j
public class ApiTest {

    @Test
    public void test_BeanFactory() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2.注册 bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 3.第一次获取 bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        log.info("bean对象：{}", userService);
        userService.queryUserInfo();

        // 4.第二次获取 bean from Singleton
        UserService userService_singleton = (UserService) beanFactory.getBean("userService");
        log.info("bean对象：{}", userService_singleton);
        userService_singleton.queryUserInfo();
    }

}
