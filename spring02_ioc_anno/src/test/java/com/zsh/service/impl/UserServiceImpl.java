package com.zsh.service.impl;

import com.zsh.dao.UserDao;
import com.zsh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

//<bean id="userService" class="com.zsh.service.impl.UserServiceImpl">
//<property name="userDao" ref="userDao"/>
//</bean>
//@Component("userServiceImpl")
@Service("userService")
@Scope("singleton")
public class UserServiceImpl implements UserService {

    //<property name="userDao" ref="userDao"/>
    //@Autowired//按照数据类型从Spring容器中进行匹配的
    //@Qualifier("userDao")//是按照id名称从容器中进行匹配 但是主要此处要结合Autowired一起使用
    @Resource(name="userDao")
    private UserDao userDao;
//使用注解方式 set方法可以省略不写

//    @Value("zsh")
    @Value("${jdbc.username}")
    private String driver;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save() {
        System.out.println(driver);
        userDao.save();
    }
    @PostConstruct
    public void init(){
        System.out.println("初始化方法");
    }
    @PreDestroy
    public void destory(){
        System.out.println("销毁方法");
    }
}
