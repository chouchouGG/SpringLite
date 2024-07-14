package cn.learn.aop.aspect.joinpoint;

public interface Joinpoint {

    Object proceed() throws Throwable;

}
