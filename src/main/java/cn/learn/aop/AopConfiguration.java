package cn.learn.aop;

import cn.learn.aop.pointcut.MethodMatcher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * 该类封装了支持 AOP 代理所需的配置，包括被代理的目标对象、方法拦截器和方法匹配器。
 */
@Getter
@Setter
public class AopConfiguration {
    /**
     * 被代理的目标对象
     */
    private TargetSource targetSource;

    /**
     * 方法拦截器
     */
    private MethodInterceptor methodInterceptor;

    /**
     * 方法匹配器(检查目标方法是否符合通知条件)
     */
    private MethodMatcher methodMatcher;

    /**
     * {@link TargetSource} 作为静态内部类，用于在包装AOP中的目标对象
     */
    @AllArgsConstructor
    @Getter
    public static class TargetSource {

        /**
         * 目标对象，即被代理的对象
         */
        private final Object target;

        public Class<?>[] getInterfacesFromTargetSource(){
            return this.target.getClass().getInterfaces();
        }
    }

}
