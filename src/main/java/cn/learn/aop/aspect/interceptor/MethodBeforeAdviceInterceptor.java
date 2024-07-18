package cn.learn.aop.aspect.interceptor;

import cn.learn.annotation.Autowired;
import cn.learn.aop.aspect.joinpoint.Joinpoint;
import cn.learn.aop.aspect.advice.MethodBeforeAdvice;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 前置通知拦截器。
 * <p>{@link MethodBeforeAdviceInterceptor} 是一个 {@link MethodAdviceInterceptor}，它用来包装并调用
 * {@link MethodBeforeAdvice} 类型的通知。这里的 <code>advice</code> 是通过注入得到的实际的通知。</p>
 *
 * <p>note：拦截器模式：将通知调用逻辑与实际的方法调用逻辑分离，这样可以保持代码的模块化和可维护性。</p>
 */
public class MethodBeforeAdviceInterceptor implements MethodAdviceInterceptor {

    @Autowired
    private MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor() {
    }

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(Joinpoint joinpoint) throws Throwable {
        advice.before();
        return joinpoint.proceed();
    }

}