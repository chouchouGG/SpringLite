package cn.learn.aop.aspect.interceptor;


import cn.learn.aop.aspect.joinpoint.Joinpoint;

public interface MethodAdviceInterceptor {

    Object invoke(Joinpoint invocation) throws Throwable;

}