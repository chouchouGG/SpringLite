package cn.learn.aop;

import cn.learn.aop.aspect.advice.MethodBeforeAdvice;

import java.util.Date;

public class UserServiceBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before() {
        System.out.println(new Date() + " - 开始执行前置方法：" + this.getClass().getName());
    }

}