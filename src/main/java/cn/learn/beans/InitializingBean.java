package cn.learn.beans;

/**
 * @program: SpringLite
 * @description: 实现此接口的 Bean 对象，会在 BeanFactory 设置属性后执行特定的方法，如：执行自定义初始化，或者仅仅检查是否设置了所有强制属性。
 * @author: chouchouGG
 * @create: 2024-07-07 22:12
 **/
public interface InitializingBean {

    /**
     * Bean 处理了属性填充后调用
     *
     * @throws Exception
     */
    void afterPropertiesSet() throws Exception;

}