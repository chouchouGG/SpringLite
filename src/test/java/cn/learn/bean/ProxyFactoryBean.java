package cn.learn.bean;

import cn.learn.beans.factoryBean.FactoryBean;
import cn.learn.util.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProxyFactoryBean implements FactoryBean<IUserDao> {

    @Override
    public IUserDao getObject() {
        // 使用匿名内部类创建InvocationHandler实例
        InvocationHandler handler = new InvocationHandler() {
            private final Map<String, String> hashMap = new HashMap<>();

            {
                hashMap.put("10001", "小傅哥");
                hashMap.put("10002", "八杯水");
                hashMap.put("10003", "阿毛");
            }

            @Override
            public Object invoke(Object proxy, java.lang.reflect.Method method, Object[] args) {
                if (method.getName().equals("queryUserName")) {
                    return hashMap.get(args[0].toString());
                } else if (method.getName().equals("queryAllUserNames")) {
                    return new ArrayList<>(hashMap.values());
                } else if (method.getName().equals("toString")) {
                    return super.toString();
                } else {
                    throw new RuntimeException("未匹配到方法" + method.getName());
                }
            }
        };

        return (IUserDao) Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), new Class[]{IUserDao.class}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        // note: 决定代理的对象是单例模式还是原型模式
        return true;
    }

}
