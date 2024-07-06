package cn.learn.core.io;

public interface ResourceLoader {

    /**
     * 从类路径加载的伪URL前缀:" classpath:"
     */
    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 根据给定的位置加载资源。
     *
     * @param location 资源的位置，可以是绝对路径、相对路径、类路径或者URL
     * @return 返回资源对象
     */
    Resource getResource(String location);

}