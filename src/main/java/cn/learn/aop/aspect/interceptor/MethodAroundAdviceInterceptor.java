package cn.learn.aop.aspect.interceptor;

import cn.learn.annotation.Autowired;
import cn.learn.aop.aspect.advice.MethodAroundAdvice;
import cn.learn.aop.aspect.joinpoint.Joinpoint;
import cn.learn.aop.aspect.joinpoint.ProceedingJoinPoint;

/**
 * 环绕通知拦截器
 **/
public class MethodAroundAdviceInterceptor implements MethodAdviceInterceptor {

    @Autowired
    private MethodAroundAdvice advice;

    public MethodAroundAdviceInterceptor() {
    }

    public MethodAroundAdviceInterceptor(MethodAroundAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(Joinpoint joinPoint) {
        return advice.around((ProceedingJoinPoint) joinPoint);
    }
}
