package cn.learn.aop.aspect.joinpoint;

import lombok.AllArgsConstructor;

import java.lang.reflect.Method;

/**
 * {@link ProceedingJoinPoint} 类封装了目标对象、方法和参数，并通过反射机制调用目标方法。
 */
@AllArgsConstructor
public class ProceedingJoinPoint implements Joinpoint {

    // 目标对象
    protected final Object target;

    // 方法
    protected final Method method;

    // 入参
    protected final Object[] arguments;

    /**
     * 通过反射调用目标方法
     */
    @Override
    public Object proceed() throws Throwable {
        return method.invoke(target, arguments);
    }

}
