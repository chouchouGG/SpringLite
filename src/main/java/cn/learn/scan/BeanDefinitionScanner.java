package cn.learn.scan;

import cn.hutool.core.util.StrUtil;
import cn.learn.annotation.Component;
import cn.learn.annotation.Scope;
import cn.learn.beans.entity.BeanDefinition;
import cn.learn.beans.registry.BeanDefinitionRegistry;

import java.util.Set;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-14 22:01
 **/
public class BeanDefinitionScanner extends ComponentScanner {

    private final BeanDefinitionRegistry registry;

    public BeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> components = super.scanForComponents(basePackage);
            for (BeanDefinition beanDefinition : components) {
                // 解析 Bean 的作用域： singleton、prototype
                String beanScope = resolveBeanScope(beanDefinition);
                if (StrUtil.isNotEmpty(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }
                // 注册 BeanDefinition 到 Bean 工厂
                registry.registerBeanDefinition(resolveBeanName(beanDefinition), beanDefinition);
            }
        }
    }

    /**
     * 获取Bean的作用域：singleton、prototype
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (null != scope) {
            return scope.value();
        }
        return StrUtil.EMPTY;
    }

    /**
     * 获取Bean的名称，如果没有配置，默认为类名的首字母小写
     */
    private String resolveBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }

}
