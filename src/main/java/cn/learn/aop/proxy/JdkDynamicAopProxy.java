package cn.learn.aop.proxy;

import cn.learn.aop.AopConfiguration;
import cn.learn.aop.ReflectiveMethodInvocation;
import cn.learn.aop.pointcut.MethodMatcher;
import org.aopalliance.intercept.MethodInterceptor;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 基于 JDK 实现的代理类
 */
public class JdkDynamicAopProxy extends AbstractAopProxy implements InvocationHandler {

    private final AopConfiguration aopConfig;

    public JdkDynamicAopProxy(AopConfiguration aopConfig) {
        this.aopConfig = aopConfig;
    }

    /**
     * <p>invoke 方法是 Java 反射 API 中 InvocationHandler 接口的核心方法之一。它被动态代理调用，用于拦截方法调用并在调用目标方法之前和之后执行一些逻辑。</p>
     *
     * @param proxy  代理实例，即动态代理生成的代理对象
     * @param method 被调用的方法对象，可以通过 method.invoke 来调用目标方法
     * @param args   方法调用时传递的参数数组，包含实际传递给方法的参数值
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 1. 获取目标对象 TargetSource、方法拦截器 MethodInterceptor 和方法匹配器 MethodMatcher
        AopConfiguration.TargetSource targetSource = aopConfig.getTargetSource();
        MethodInterceptor methodInterceptor = aopConfig.getMethodInterceptor();
        MethodMatcher methodMatcher = aopConfig.getMethodMatcher();

        // 2. 从目标对象 TargetSource 获取实际的目标对象实例
        Object target = targetSource.getTarget();

        // 3. 获取相应的Class对象
        Class<?> clazz = target.getClass();

        // 4. 检查方法是否匹配切入点表达式
        // 4.1 若匹配
        boolean matche = methodMatcher.matches(method, clazz);
        if (matche) {
            // 5. 创建 ReflectiveMethodInvocation 对象，封装目标对象（target）、方法（method）和参数（args）。
            ReflectiveMethodInvocation argsForInvoke = new ReflectiveMethodInvocation(target, method, args);

            // 6. 调用方法拦截器的 invoke 方法，执行自定义逻辑，并调用原目标方法。
            return methodInterceptor.invoke(argsForInvoke);
        }

        // 4.2 若不匹配，则直接通过反射调用目标对象的原目标方法
        return method.invoke(target, args);
    }

    public void saveProxyClass(Proxy proxy, String path) {
        String proxyName = proxy.getClass().getName();
        byte[] proxyClass = ProxyGenerator.generateProxyClass(proxyName, getTargetClassInterfaces());
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path + proxyName + ".class");
            out.write(proxyClass);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out == null) {
                try {
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    protected Class<?>[] getTargetClassInterfaces() {
        return this.aopConfig.getTargetSource().getInterfacesFromTargetSource();
    }

    @Override
    protected InvocationHandler getInvocationHandler() {
        return this;
    }
}
