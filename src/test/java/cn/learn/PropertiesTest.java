package cn.learn;

import cn.learn.core.io.loader.DefaultResourceLoader;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-14 19:52
 **/
public class PropertiesTest {

    @Test
    public void test_load() throws IOException {
        String loacation = "src/test/resources/application.properties";
        Properties properties = new Properties();
        properties.load(DefaultResourceLoader.loadResourceLocationAsStream(loacation));

        String strVal = "${database.username}${database.password}";
        // 检查是否是单个占位符
        if (strVal.startsWith("${") && strVal.endsWith("}")) {
            // 去掉开头和结尾的占位符符号，提取占位符中的键
            String propKey = strVal.substring(2, strVal.length() - 1);
            String propVal = properties.getProperty(propKey);

            if (propVal != null) {
                System.out.println(propVal);
            } else {
                System.out.println("配置文件中未找到占位符的键: " + propKey);
            }
        } else {
            throw new IllegalArgumentException("配置的占位符有错误，请检查占位符的格式：${XXX}");
        }
    }
}
