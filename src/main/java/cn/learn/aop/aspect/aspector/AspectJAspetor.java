package cn.learn.aop.aspect.aspector;

import cn.learn.aop.aspect.interceptor.MethodAdviceInterceptor;
import cn.learn.aop.aspect.pointcut.AspectJPointcutor;
import cn.learn.aop.aspect.pointcut.Pointcutor;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


/**
 * <p>本质是表示切面的类：包装了切入点表达式 {@link AspectJAspetor#pointcutor}
 * 和拦截器  {@link AspectJAspetor#advice}，
 * 拦截器就可以看作是通知，不过是对通知调用逻辑的进一步封装。</p>
 *
 */
@NoArgsConstructor
@AllArgsConstructor
public class AspectJAspetor implements Aspector {

    /**
     * 切入点：切入点用于定义在哪些连接点（Joinpoints）上应用通知，AspectJPointcutor 类用于封装切入点表达式。
     */
    private Pointcutor pointcutor;

    /**
     * 通知：通知是实际执行的代码，当切入点匹配时，通知将被执行。这里的通知用于接入方法拦截器(适配器)，由适配器接入的用户自定义的通知拦截方法
     */
    private MethodAdviceInterceptor advice;

    /**
     * 切入点表达式：
     * <p>note：思考这里为什么有了 <code>pointcutor</code>，还需要字符串的切入点表达式？</p>
     * <p>答：expression 是通过配置文件设置的属性值直接配置的，只是作为暂存，pointcut 是后续AOP过程真正使用的。</p>
     *
     */
    private String expression;

    public Pointcutor getPointcutor() {
        if (null == pointcutor) {
            pointcutor = new AspectJPointcutor(expression);
        }
        return pointcutor;
    }

    public MethodAdviceInterceptor getAdviceInterceptor() {
        return this.advice;
    }

}
 