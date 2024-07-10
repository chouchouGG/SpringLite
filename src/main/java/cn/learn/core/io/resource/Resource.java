package cn.learn.core.io.resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * 定义一个统一的接口来访问各种类型的资源（例如文件、URL、类路径资源），使得具体资源的访问方式对客户端透明。
 */
public interface Resource {

    /**
     * 获取资源的输入流。
     *
     * @return 资源的输入流
     * @throws IOException 如果资源无法打开或读取
     */
    InputStream getInputStream() throws IOException;

}
