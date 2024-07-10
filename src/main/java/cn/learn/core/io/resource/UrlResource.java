package cn.learn.core.io.resource;

import cn.hutool.core.lang.Assert;
import cn.learn.core.io.resource.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class UrlResource implements Resource {

    private final URL url;

    public UrlResource(URL url) {
        Assert.notNull(url, "URL 不能为空");
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        // 1. 先通过url获取连接
        URLConnection con = this.url.openConnection();
        try {
            // 2. 根据连接获取输入流
            return con.getInputStream();
        } catch (IOException ex) {
            // 异常处理：确保了在发生 IO 异常时，HTTP 连接被正确关闭，避免资源泄漏。
            if (con instanceof HttpURLConnection) {
                ((HttpURLConnection) con).disconnect();
            }
            throw ex;
        }
    }

}