package cn.learn.aop.entity;

import cn.learn.aop.aspect.interceptor.MethodAdviceInterceptor;
import cn.learn.aop.aspect.pointcut.MethodMatcher;
import cn.learn.aop.proxy.ProxyFactory;
import lombok.*;

/**
 * 该类封装了支持 AOP 代理所需的配置，包括被代理的目标对象、方法拦截器和方法匹配器。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AopProxyConfig {

    /**
     * 目前只使用JDK动态代理，不使用Cglib的代理，不对代理的方式进行区分代码在 {@link ProxyFactory#getProxy(AopProxyConfig)} 中有体现
     */
    private final boolean DEFAULT_JDK_PROXY = true;

    /**
     * 被代理的目标对象（用于存储目标对象的一些状态信息）
     */
    private TargetSource targetSource;

    /**
     * 方法拦截器（封装通知的整体调用逻辑）
     */
    private MethodAdviceInterceptor methodInterceptor;

    /**
     * 方法匹配器（检查目标方法是否匹配切入点表达式）：切入点表达式中的类类型匹配，证明需要Aop代理，只有方法匹配才需要执行相应通知
     */
    private MethodMatcher methodMatcher;


    public boolean isDefaultJdkProxy() {
        return this.DEFAULT_JDK_PROXY;
    }

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

        public String getClassName() {
            return target.getClass().getName();
        }
    }

}
