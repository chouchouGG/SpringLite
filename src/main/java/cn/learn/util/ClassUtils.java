package cn.learn.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassUtils {

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = null;
        // 1. 尝试获取当前线程的上下文类加载器
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
             log.warn("无法访问线程上下文ClassLoader - 回退到系统类加载器", ex);
        }
        // 2. 如果获取不到，则使用当前类 (ClassUtils) 的类加载器
        return classLoader == null ? ClassUtils.class.getClassLoader() : classLoader;
    }

}
