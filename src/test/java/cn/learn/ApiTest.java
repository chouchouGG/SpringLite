package cn.learn;

import cn.learn.bean.UserDao;
import cn.learn.bean.UserService;
import cn.learn.beans.factory.config.PropertyValue;
import cn.learn.beans.factory.config.PropertyValues;
import cn.learn.beans.factory.config.BeanDefinition;
import cn.learn.beans.factory.config.BeanReference;
import cn.learn.beans.factory.DefaultListableBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


@Slf4j
public class ApiTest {

    @Test
    public void test_BeanFactory() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. UserDao 注册
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        // 3. UserService 设置属性[uId、userDao]
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "10003"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

        // 4. UserService 注入bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 5. UserService 获取bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }

}
