package cn.learn.aop.proxy;

import cn.learn.util.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-12 09:45
 **/
public abstract class AbstractAopProxy implements AopProxy {

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), getTargetClassInterfaces(), getInvocationHandler());
    }

    /**
     *  获取目标类实现的所有接口。
     */
    protected abstract Class<?>[] getTargetClassInterfaces();

    /**
     * <p>该方法用于返回一个 {@link InvocationHandler}，它定义了代理对象在调用目标方法之前或之后执行额外的逻辑。</p>
     */
    protected abstract InvocationHandler getInvocationHandler();
}
