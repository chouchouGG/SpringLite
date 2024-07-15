package cn.learn.beans.entity;


import java.util.*;

/**
 * @program: SpringLite
 * @description: 包装 PropertyValue 类为一个集合
 * @author: chouchouGG
 * @create: 2024-07-05 14:13
 **/
public class PropertyValues {

    private final Map<String, Object> propertyValueMap = new HashMap<>();

    /**
     * 向List中添加属性值
     */
    public void addPropertyValue(String key, Object value) {
        propertyValueMap.put(key, value);
    }

    /**
     * 获取属性值数组
     */
    public Set<Map.Entry<String, Object>> getPropertyValueEntries() {
        return propertyValueMap.entrySet();
    }

    /**
     * 根据属性值名称获取对应的属性值
     */
    public Object getPropertyValue(String propertyName) {
        return propertyValueMap.get(propertyName);
    }
}
