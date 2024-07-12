package cn.learn.aop.pointcut;

import java.lang.reflect.Method;

/**
 * {@link Pointcut} 的组成部分: 来确定给定的方法是否符合匹配器的条件
 */
public interface MethodMatcher {

    boolean matches(Method method, Class<?> targetClass);
    
}
