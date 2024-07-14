package cn.learn.aop.aspect.advice;

import cn.learn.aop.aspect.joinpoint.ProceedingJoinPoint;

public interface MethodAroundAdvice extends AroundAdvice {

    Object around(ProceedingJoinPoint joinPoint);
}