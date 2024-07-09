package cn.learn.bean;

import cn.hutool.core.util.ReflectUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-05 14:38
 **/
public class UserDao {

    private static Map<String, String> hashMap = new HashMap<>();

    public void initDataMethod() {
        System.out.println("UserDao 执行：init-method");
        hashMap.put("10001", "小傅哥");
        hashMap.put("10002", "八杯水");
        hashMap.put("10003", "阿毛");
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }

    public void destroyDataMethod() {
        System.out.println("UserDao 执行：destroy-method");
        hashMap.clear();
    }

}
