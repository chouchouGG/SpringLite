package cn.learn.context.impl;

import cn.learn.beans.entity.BeanDefinition;
import cn.learn.exception.BeansException;
import cn.learn.beanfactory.factory.DefaultListableBeanFactory;
import lombok.Getter;

@Getter
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private final DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();;

    @Override
    protected void createBeanFactoryAndLoadBeanDefinition() throws BeansException {
        loadBeanDefinitions();
        print();
    }

    /**
     * 读取Bean对象的配置文件并解析，完成Bean的加载（注册）
     */
    protected abstract void loadBeanDefinitions();

    /**
     * 控制台打印，加载的bean的日志信息
     */
    private void print() {
        String[] beanNames = beanFactory.getBeanDefinitionNames();
        System.out.println("注册的 Beans:");
        for (String beanName : beanNames) {
            // fully qualified class name
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            String fqcn = beanDefinition.getBeanClass().getName();
            System.out.println(beanName + " - " + fqcn + " - " + beanDefinition.getScope());
        }
    }
}