package cn.learn.home;

import cn.learn.aop.aspect.advice.MethodBeforeAdvice;

import java.util.Date;

public class SpouseAdvice implements MethodBeforeAdvice {

    @Override
    public void before() throws Throwable {
        System.out.println("关怀小两口(切面)" + new Date());
    }

}