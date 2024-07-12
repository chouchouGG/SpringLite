package cn.learn.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;


@Getter
@Setter
public class UserService implements IUserService {

    private String uId;
    private String company;
    private String location;
    private IUserDao userDao; // 模拟Dao数据访问层

    @Override
    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "小傅哥，123456，Alibaba";
    }

    @Override
    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "注册用户：" + userName + " success！";
    }

//    @Override
//    public String queryUserInfo() {
//        return userDao.queryUserName(uId) + "," + company + "," + location;
//    }

    @Override
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
