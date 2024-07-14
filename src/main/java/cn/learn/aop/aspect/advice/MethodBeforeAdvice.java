package cn.learn.aop.aspect.advice;

/**
 * 环绕 Advice 类似一个拦截器的链路，Before Advice、After advice等，不过暂时我们不需要那么多，就只定义了一个 MethodBeforeAdvice 的接口定义。
 */
public interface MethodBeforeAdvice extends BeforeAdvice {

    /**
     * 在目标方法执行之前被调用
     */
    void before() throws Throwable;

}
