package cn.learn.factory.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: SpringLite
 * @description: note：BeanReference 用于指定要注入的 Bean 的名称，与注册 Bean 时的 BeanDefinition 的名称匹配
 * @author: chouchouGG
 * @create: 2024-07-05 14:28
 **/
@Getter
@AllArgsConstructor
public class BeanReference {

    private final String beanName;

}
