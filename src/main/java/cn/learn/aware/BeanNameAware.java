package cn.learn.aware;

/**
 * 实现此接口的 bean，能感知到其在 bean 工厂中的名字
 */
public interface BeanNameAware extends Aware {

    void setBeanName(String name);

}

