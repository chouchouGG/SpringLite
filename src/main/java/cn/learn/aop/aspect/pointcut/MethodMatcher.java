package cn.learn.aop.aspect.pointcut;

import java.lang.reflect.Method;

/**
 * {@link Pointcutor} 的组成部分: 来确定给定的方法是否符合匹配器的条件
 */
public interface MethodMatcher {

    boolean methodMatch(Method method, Class<?> targetClass);
    
}
