package cn.learn.context.impl;

import cn.learn.beans.entity.BeanDefinition;
import cn.learn.exception.BeansException;
import cn.learn.factory.impl.DefaultListableBeanFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        beanFactory = new DefaultListableBeanFactory();
        loadBeanDefinitions();
        print();
    }

    /**
     * 读取Bean对象的配置文件并解析，完成Bean的加载（注册）
     */
    protected abstract void loadBeanDefinitions();

    // 控制台打印
    private void print() {
        String[] beanNames = beanFactory.getBeanDefinitionNames();
        System.out.println("注册的 Beans:");
        for (String beanName : beanNames) {
            // fully qualified class name
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            String fqcn = beanDefinition.getBeanClass().getName();
            System.out.println(" - " + fqcn + " - " + beanDefinition.getScope());
        }
    }
}