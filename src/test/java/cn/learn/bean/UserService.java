package cn.learn.bean;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserService {


    private String uId;

    private Integer age;

    // 模拟Dao数据访问层
    private UserDao userDao;

    public void queryUserInfo() {
        System.out.println("查询用户信息：" + userDao.queryUserName(uId));
    }

    @Override
    public String toString() {
        return "UserService{" + "uId='" + uId + '\'' + ", age=" + age + ", userDao=" + userDao + '}';
    }
}
