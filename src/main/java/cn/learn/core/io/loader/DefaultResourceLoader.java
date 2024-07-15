package cn.learn.core.io.loader;

import cn.hutool.core.lang.Assert;
import cn.learn.core.io.resource.ClassPathResource;
import cn.learn.core.io.resource.FileSystemResource;
import cn.learn.core.io.resource.Resource;
import cn.learn.core.io.resource.UrlResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DefaultResourceLoader implements ResourceLoader {

    /**
     * 加载资源并返回输入流，该静态方法提供更加简便的方式获取资源输入流
     *
     * @param location 资源位置
     */
    public static InputStream loadResourceLocationAsStream(String location) {
        try {
            return new DefaultResourceLoader().getResource(location).getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("未能加载资源：" + location, e);
        }
    }

    @Override
    public Resource getResource(String location) {
        // 确保位置参数不为null
        Assert.notNull(location, "Location must not be null");

        // 如果位置是以 "classpath:" 开头，则从类路径中加载资源
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        }
        else {
            try {
                // 尝试将位置解释为 URL
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                // 否则尝试从文件系统加载资源
                return new FileSystemResource(location);
            }
        }
    }

}