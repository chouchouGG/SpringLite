package cn.learn.aop.aspect.interceptor;

import cn.learn.aop.aspect.joinpoint.Joinpoint;
import cn.learn.aop.aspect.joinpoint.ProceedingJoinPoint;
import cn.learn.aop.aspect.advice.MethodAroundAdvice;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 环绕通知拦截器
 **/
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MethodAroundAdviceInterceptor implements MethodAdviceInterceptor {

    private MethodAroundAdvice advice;

    @Override
    public Object invoke(Joinpoint joinPoint) {
        return advice.around((ProceedingJoinPoint) joinPoint);
    }
}
