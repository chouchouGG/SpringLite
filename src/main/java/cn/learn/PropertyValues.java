package cn.learn;


import java.util.ArrayList;
import java.util.List;

/**
 * @program: SpringLite
 * @description: 包装 PropertyValue 类为一个集合
 * @author: chouchouGG
 * @create: 2024-07-05 14:13
 **/
public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    /**
     * 向List中添加属性值
     */
    public void addPropertyValue(PropertyValue pv) {
        this.propertyValueList.add(pv);
    }

    /**
     * 获取属性值数组
     */
    public PropertyValue[] getPropertyValuesArray() {
        return propertyValueList.toArray(new PropertyValue[0]);
    }

    /**
     * 根据属性值名称获取对应的属性值
     */
    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : propertyValueList) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }
}
