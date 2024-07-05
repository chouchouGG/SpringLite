package cn.learn.bean;

/**
 * 作者：DerekYRC https://github.com/DerekYRC/mini-spring

https://github.com/DerekYRC/mini-spring
https://github.com/code4craft/tiny-spring
 */
public class UserService {


    private String name;

    public UserService(String name) {
        this.name = name;
    }

    public void queryUserInfo() {
        System.out.println("查询用户信息：" + name);
    }

    @Override
    public String toString() {
        return "" + name;
    }

}
