package cn.learn;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.learn.bean.UserDao;
import cn.learn.bean.UserService;
import cn.learn.beans.factory.config.PropertyValue;
import cn.learn.beans.factory.config.PropertyValues;
import cn.learn.beans.factory.config.BeanDefinition;
import cn.learn.beans.factory.config.BeanReference;
import cn.learn.beans.factory.support.DefaultListableBeanFactory;
import cn.learn.beans.factory.support.registry.BeanDefinitionRegistry;
import cn.learn.beans.factory.xml.XmlBeanDefinitionReader;
import cn.learn.core.io.Resource;
import cn.learn.core.io.impl.DefaultResourceLoader;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;


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

    private DefaultResourceLoader resourceLoader;

    @Before
    public void init() {
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void test_classpath() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_file() throws IOException {
        Resource resource = resourceLoader.getResource("src/test/resources/spring.xml");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_xml() {
        // 1.初始化 Bean 的注册器和 Bean的加载器
        DefaultListableBeanFactory registry = new DefaultListableBeanFactory();
        DefaultResourceLoader loader = new DefaultResourceLoader();

        // 2. 读取配置文件&注册Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry, loader);
        reader.loadBeanDefinitions("classpath:spring.xml");

        // 3. 获取 Bean 对象调用方法
        UserService userService = registry.getBean("userService", UserService.class);
        System.out.println(userService.toString());
        userService.queryUserInfo();
    }
}
