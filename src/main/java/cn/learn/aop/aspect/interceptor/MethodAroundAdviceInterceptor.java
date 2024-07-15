package cn.learn.aop.aspect.interceptor;

import cn.learn.annotation.Component;
import cn.learn.aop.aspect.joinpoint.Joinpoint;
import cn.learn.aop.aspect.joinpoint.ProceedingJoinPoint;
import cn.learn.aop.aspect.advice.MethodAroundAdvice;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 环绕通知拦截器
 **/
public class MethodAroundAdviceInterceptor implements MethodAdviceInterceptor {

    private MethodAroundAdvice advice;

    public MethodAroundAdviceInterceptor() {
    }

    public MethodAroundAdviceInterceptor(MethodAroundAdvice advice) {
        this.advice = advice;
    }

    public MethodAroundAdvice getAdvice() {
        return advice;
    }

    public void setAdvice(MethodAroundAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(Joinpoint joinPoint) {
        return advice.around((ProceedingJoinPoint) joinPoint);
    }
}
