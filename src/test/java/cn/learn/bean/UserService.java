package cn.learn.bean;

import cn.learn.annotation.Autowired;
import cn.learn.annotation.Component;
import cn.learn.annotation.Scope;
import cn.learn.annotation.Value;
import lombok.Data;


@Component("userService")
@Scope
@Data
public class UserService implements IUserService {

    @Value("${token}")
    private String token;

    @Autowired
    private UserDao userDao;

    public String queryUserInfo() {
        return userDao.queryUserName("10002") + "，" + token;
    }

}
