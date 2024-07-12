package cn.learn.aop.pointcut;


/**
 * 表示切入点的接口。
 * <p>切入点是匹配连接点(此处的方法执行)的谓词。切入点由两部分组成: {@link ClassFilter} 和 {@link MethodMatcher}。</p>
 *
 * <p>{@link ClassFilter} 限制匹配特定的目标类，而 {@link MethodMatcher} 限制目标类中特定的方法。</p>
 *
 * <p>这个接口提供了访问 {@link ClassFilter} 和 {@link MethodMatcher} 的方法。</p>
 */
public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();

}
