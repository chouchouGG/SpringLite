package cn.learn.beans.entity;

/**
 * @program: SpringLite
 * @description: note: 当PropertyValues内部使用HashMap，而非列表后，就不需要PropertyValue
 * @author: chouchouGG
 * @create: 2024-07-05 14:10
 **/
@Deprecated
public class PropertyValue {

    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
