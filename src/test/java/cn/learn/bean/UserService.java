package cn.learn.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class UserService {

    private String uId;
    private String company;
    private String location;
    private IUserDao userDao; // 模拟Dao数据访问层

    public String queryUserInfo() {
        return userDao.queryUserName(uId) + "," + company + "," + location;
    }

    public List<String> queryAllUserNames() {
        return userDao.queryAllUserNames();
    }

    @Override
    public String toString() {
        return super.toString() + " " +
                "UserService{" +
                "uId='" + uId + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", userDao=" + userDao +
                '}';
    }
}
