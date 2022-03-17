package com.zsh.test;

import com.zsh.dao.UserDao;
import com.zsh.dao.impl.UserDaoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
    @Test
    //测试scope属性
    public void test1(){
        ApplicationContext app=new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao) app.getBean("userDao");

        System.out.println(userDao);
        ((ClassPathXmlApplicationContext) app).close();
    }
}
