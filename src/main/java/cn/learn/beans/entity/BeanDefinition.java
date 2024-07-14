package cn.learn.beans.entity;

import cn.learn.exception.BeansException;
import cn.learn.beanfactory.ConfigurableBeanFactory;
import lombok.Getter;
import lombok.Setter;


/**
 * @program: SpringLite
 * @description: 包装注册到容器中的信息
 * @author: chouchouGG
 * @create: 2024-07-04 17:52
 **/
@Getter
@Setter
public class BeanDefinition {

    private Class beanClass;

    // 这两个字段是为了能在spring.xml的<bean>标签中配置'init-method'和'destory-method'两个属性
    private String initMethodName;

    private String destroyMethodName;

    private String scope = ConfigurableBeanFactory.SCOPE_SINGLETON;

    private PropertyValues propertyValues = new PropertyValues();

    private boolean singleton = true;

    private boolean prototype = false;

    public void setScope(String scope) {
        this.scope = scope;
        if (scope.equals(ConfigurableBeanFactory.SCOPE_SINGLETON)) {
            singleton = true;
            prototype = false;
        } else if (scope.equals(ConfigurableBeanFactory.SCOPE_PROTOTYPE)) {
            singleton = false;
            prototype = true;
        } else {
            throw new BeansException(beanClass.getName() + "无效的配置, 作用域 scope: " + scope);
        }
    }

    public boolean isSingleton() {
        return singleton;
    }

}
