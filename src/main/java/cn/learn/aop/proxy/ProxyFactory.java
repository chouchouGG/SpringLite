package cn.learn.aop.proxy;

import cn.learn.aop.entity.AopProxyConfig;
import cn.learn.aop.proxy.AopProxy;
import cn.learn.aop.proxy.JdkDynamicAopProxy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 代理工厂：封装了不同方式创建代理的操作，目前由于只使用JDK的动态代理，所以该工厂相当于只是用于创建JDK的动态代理对象
 */
@Slf4j
public class ProxyFactory {

    public static Object getProxy(AopProxyConfig config) {
        // note：目前默认只使用JDK的动态代理方式，Cglib暂时没有了解过，也未实现
        if (config.isDefaultJdkProxy()) {
            log.debug("使用默认JDK动态代理，代理：{}", config.getTargetSource().getClassName());
            return new JdkDynamicAopProxy(config).getProxy();
        } else {
            log.debug("【暂未实现】使用其他的代理方式...，代理：{}", config.getTargetSource().getClassName());
            // 使用其他的代理方式...
            return null;
        }
    }

}