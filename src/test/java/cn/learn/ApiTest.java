package cn.learn;

import cn.learn.bean.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


@Slf4j
public class ApiTest {

    @Test
    public void test_BeanFactory() {
        // 1.初始化 BeanFactory
        log.info("初始化 BeanFactory");
        BeanFactory beanFactory = new BeanFactory();

        // 2.注入bean
        log.info("注入 Bean 定义");
        BeanDefinition beanDefinition = new BeanDefinition(new UserService());
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 3.获取bean
        log.info("获取 Bean 实例");
        UserService userService = (UserService) beanFactory.getBean("userService");
        log.info("获取的 Bean 实例: {}", userService);

        userService.queryUserInfo();
    }

}
