package cn.learn.factory.config;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-04 17:52
 **/
public class BeanDefinition {

    private Class beanClass;

    // 构造方法
    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    // Getter
    public Class getBeanClass() {
        return beanClass;
    }

    // Setter
    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
