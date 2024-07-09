package cn.learn;

import cn.learn.bean.UserService;
import cn.learn.context.impl.ClassPathXmlApplicationContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


@Slf4j
public class ApiTest {

    @Test
    public void test_aware() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        UserService userService = applicationContext.getBean("userService", UserService.class);

        String result = userService.queryUserInfo();

        System.out.println("测试结果：" + result);
        System.out.println("ApplicationContextAware：" + userService.getApplicationContext());
        System.out.println("BeanFactoryAware：" + userService.getBeanFactory());
    }


}
