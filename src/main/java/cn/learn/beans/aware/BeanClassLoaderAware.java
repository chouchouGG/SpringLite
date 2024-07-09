package cn.learn.beans.aware;


/**
 * 这个接口是一个感知接口，用于让希望了解其 ClassLoader 的 Bean 实现。实现此接口的类将会被赋予加载它们的类加载器。
 * 当 Bean 需要动态加载资源或类时，这个接口非常有用。
 */
public interface BeanClassLoaderAware extends Aware{

    /**
     * 将所属工厂的类装入器提供给bean实例。
     * @param classLoader
     */
    void setBeanClassLoader(ClassLoader classLoader);

}


    