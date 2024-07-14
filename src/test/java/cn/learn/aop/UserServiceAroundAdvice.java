package cn.learn.aop;

import cn.learn.aop.aspect.advice.MethodAroundAdvice;
import cn.learn.aop.aspect.joinpoint.ProceedingJoinPoint;

import java.util.Date;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-14 10:38
 **/
public class UserServiceAroundAdvice implements MethodAroundAdvice {
    @Override
    public Object around(ProceedingJoinPoint joinPoint) {
        System.out.println(new Date() + " - 开始执行环绕方法：" + this.getClass().getName());
        Object ret = null;
        try {
            ret = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        System.out.println(new Date() + " - 结束执行环绕方法：" + this.getClass().getName());
        return ret;
    }
}
