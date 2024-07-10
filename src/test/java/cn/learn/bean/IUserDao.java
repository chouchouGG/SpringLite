package cn.learn.bean;

import java.util.List;

public interface IUserDao {

    String queryUserName(String uId);

    List<String> queryAllUserNames();

}
