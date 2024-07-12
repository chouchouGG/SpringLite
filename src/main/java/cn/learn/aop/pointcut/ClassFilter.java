package cn.learn.aop.pointcut;

/**
 * {@link Pointcut} 的组成部分：类过滤器用于将切点限制在特定的目标类上。
 *
 */
public interface ClassFilter {

    /**
     * 决定是否切入点适用于给定的目标类
     *
     * @param clazz 待检查的目标类
     * @return 如果能适用于返回 {@code true} ，否则返回 {@code false}
     */
    boolean matches(Class<?> clazz);

}
