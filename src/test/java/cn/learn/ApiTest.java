package cn.learn;

import cn.learn.bean.UserService;
import cn.learn.context.impl.ClassPathXmlApplicationContext;
import cn.learn.event.CustomEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


@Slf4j
public class ApiTest {

    @Test
    public void test_prototype() {
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        // 2. 获取Bean对象调用方法
        UserService userService01 = applicationContext.getBean("userService", UserService.class);
        UserService userService02 = applicationContext.getBean("userService", UserService.class);
        UserService userService03 = applicationContext.getBean("userService", UserService.class);

        // 3. 配置 scope="prototype/singleton"
        System.out.println(userService01);
        System.out.println(userService02);
        System.out.println(userService03);

    }

    @Test
    public void test_factory_bean() {
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        // 2. 调用代理方法
        UserService userService = (UserService) applicationContext.getBean("userService", UserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
        userService.queryAllUserNames().forEach(s -> System.out.print(s + " "));

    }

    @Test
    public void test_event() {
        String configLocation = "classpath:springEvent.xml";
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocation);
        CustomEvent event = new CustomEvent(applicationContext, 1019129009086763L, "成功了！");
        applicationContext.publishEvent(event);

        applicationContext.registerShutdownHook();
    }

}
