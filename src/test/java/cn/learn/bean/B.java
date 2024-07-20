package cn.learn.bean;

import cn.learn.annotation.Autowired;
import cn.learn.annotation.Component;

/**
 * @program: SpringLite
 * @description:
 * @author: chouchouGG
 * @create: 2024-07-17 08:39
 **/
@Component("b")
public class B {

    @Autowired
    A a;

}
