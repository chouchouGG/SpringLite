package cn.learn.bean;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import cn.learn.beans.DisposableBean;
import cn.learn.beans.InitializingBean;
import cn.learn.beans.aware.ApplicationContextAware;
import cn.learn.beans.aware.BeanClassLoaderAware;
import cn.learn.beans.aware.BeanFactoryAware;
import cn.learn.beans.aware.BeanNameAware;
import cn.learn.beans.exception.BeansException;
import cn.learn.beans.factory.BeanFactory;
import cn.learn.context.ApplicationContext;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserService implements ApplicationContextAware, BeanFactoryAware, BeanNameAware, BeanClassLoaderAware {
    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;
    private String beanName;
    private ClassLoader beanClassLoader;

    private String uId;
    private String company;
    private String location;
    private UserDao userDao; // 模拟Dao数据访问层

    public String queryUserInfo() {
        return JSONUtil.toJsonStr(this);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        System.out.println("Bean Name is：" + name);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
        System.out.println("ClassLoader：" + classLoader);
    }
}
