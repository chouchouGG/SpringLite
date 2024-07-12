package cn.learn;

import cn.learn.aop.AopConfiguration;
import cn.learn.aop.pointcut.AspectJExpressionPointcut;
import cn.learn.aop.proxy.JdkDynamicAopProxy;
import cn.learn.bean.IUserService;
import cn.learn.bean.UserService;
import cn.learn.bean.UserServiceInterceptor;
import cn.learn.context.impl.ClassPathXmlApplicationContext;
import cn.learn.event.CustomEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Proxy;


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



    @Test
    public void test_dynamic() {
        // 目标对象
        IUserService userService = new UserService();

        // 组装代理信息
        AopConfiguration advisedSupport = new AopConfiguration();
        advisedSupport.setTargetSource(new AopConfiguration.TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* cn.learn.bean.IUserService.*(..))"));

        // 代理对象(JdkDynamicAopProxy)
        JdkDynamicAopProxy proxy = new JdkDynamicAopProxy(advisedSupport);
        Object proxy1 = proxy.getProxy();
        proxy.saveProxyClass((Proxy) proxy1, "./");

        // 测试调用
        System.out.println("测试结果：" + ((IUserService) proxy1).queryUserInfo());
    }

}
