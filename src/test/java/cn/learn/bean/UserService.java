package cn.learn.bean;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserService {


    private String uId;

    private String company;

    private String location;

    // 模拟Dao数据访问层
    private UserDao userDao;

    public String queryUserInfo() {
        return "用户名：" + userDao.queryUserName(uId)
                + ", 公司：" + company
                + ", 地点：" + location;
    }

    @Override
    public String toString() {
        return "UserService{" +
                "uId='" + uId + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", userDao=" + userDao +
                '}';
    }
}
