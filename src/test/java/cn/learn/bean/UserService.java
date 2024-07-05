package cn.learn.bean;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserService {


    private String uId;

    // 模拟Dao数据访问层
    private UserDao userDao;

    public void queryUserInfo() {
        System.out.println("查询用户信息：" + userDao.queryUserName(uId));
    }


}
