package cn.learn.bean;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import cn.learn.beans.DisposableBean;
import cn.learn.beans.InitializingBean;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserService implements InitializingBean, DisposableBean {

    private String uId;
    private String company;
    private String location;
    private UserDao userDao; // 模拟Dao数据访问层

    @Override
    public void destroy() {
        System.out.println("执行：UserService.destroy");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("执行：UserService.afterPropertiesSet");
    }

    public String queryUserInfo() {
        return JSONUtil.toJsonStr(this);
    }
}
