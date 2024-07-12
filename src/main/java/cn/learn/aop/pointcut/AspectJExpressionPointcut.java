package cn.learn.aop.pointcut;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link Pointcut} 实现类
 */
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<>();

    static {
        // 包含 PointcutPrimitive.EXECUTION，表示支持方法执行切点。
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    /**
     * <code>pointcutExpression</code> 表示解析后的切点表达式。PointcutExpression 是 AspectJ 库中的一个类，用于表示切点表达式。
     */
    private final PointcutExpression pointcutExpression;

    /**
     * 使用 AspectJ 的 PointcutParser 解析这个切点表达式字符串 <code>expression</code>。
     * @param expression 切点表达式字符串
     */
    public AspectJExpressionPointcut(String expression) {
        // note：创建一个支持指定原始切点类型（SUPPORTED_PRIMITIVES）的 PointcutParser，并使用当前类加载器解析类。
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());

        // 解析传入的切点表达式字符串，生成 PointcutExpression 实例
        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }

}
