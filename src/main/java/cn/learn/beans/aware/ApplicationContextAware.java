package cn.learn.beans.aware;

import cn.learn.beans.exception.BeansException;
import cn.learn.context.ApplicationContext;

/**
 * 实现此接口，既能感知到所属的 ApplicationContext
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
    