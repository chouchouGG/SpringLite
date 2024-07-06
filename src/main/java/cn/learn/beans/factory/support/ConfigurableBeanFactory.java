package cn.learn.beans.factory.support;

import cn.learn.beans.factory.singleton.SingletonBeanRegistry;
import cn.learn.beans.factory.support.HierarchicalBeanFactory;

/**
 * @program: SpringLite
 * @description: 增加了配置和管理 Bean 定义的能力。它定义了两个常用的 Bean 作用域（singleton 和 prototype）
 * @author: chouchouGG
 * @create: 2024-07-05 23:34
 **/
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

}