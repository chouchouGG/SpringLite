package cn.learn.bean;

import cn.hutool.core.date.StopWatch;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.concurrent.TimeUnit;

public class UserServiceInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        StopWatch sw = new StopWatch();

        sw.start("方法执行耗时");
        Object ret = invocation.proceed(); // 执行原目标方法
        sw.stop();

        sw.start("后续处理");
        TimeUnit.SECONDS.sleep(1);
        sw.stop();

        System.out.println(sw.prettyPrint());
        return ret;  // 返回目标方法结果
    }

}