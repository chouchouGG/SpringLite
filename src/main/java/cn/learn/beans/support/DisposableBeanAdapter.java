package cn.learn.beans.support;

import cn.hutool.core.util.StrUtil;
import cn.learn.beans.entity.BeanDefinition;
import cn.learn.exception.BeansException;

import java.lang.reflect.Method;

public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;

    private final String beanName;

    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }


    /**
     * 调用Bean对象的销毁方法，对外提供两种方式（根据 destroy 的逻辑可知：接口的优先级 > 配置的优先级）：
     * <p> 1. 在 xml 中配置销毁方法。</p>
     * <p> 2. 通过实现接口的方式处理。</p>
     */
    @Override
    public void destroy() throws Exception {
        // 1. 实现接口 DisposableBean
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        // 2. 注解配置 destroy-method（判断是为了避免二次执行销毁）
        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean)) {
            Method destroyMethod = null;
            try {
                destroyMethod = bean.getClass().getMethod(destroyMethodName);
            } catch (NoSuchMethodException e) {
                throw new BeansException("在bean对象 '" + beanName + "'上，不能找到一个命名为 '" + destroyMethodName + "' 的销毁方法");
            }
            // 调用销毁方法
            destroyMethod.invoke(bean);
        }
    }

}