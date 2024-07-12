package cn.learn.bean;

import java.util.List;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-11 15:24
 **/
public interface IUserService {

    String register(String userName);

    String queryUserInfo();

    List<String> queryAllUserNames();

}
