package cn.learn.scan;

import cn.hutool.core.util.ClassUtil;
import cn.learn.annotation.Component;
import cn.learn.beans.entity.BeanDefinition;

import java.util.LinkedHashSet;
import java.util.Set;

public class ComponentScanner {

    /**
     * 扫描带有@Component注解的类，根据类的Class对象创建BeanDefinition，并保存到Set集合
     */
    public Set<BeanDefinition> scanForComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }

}