package com.zsh.test;

import com.zsh.config.springConfiguration;
import com.zsh.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class UserController {
    public static void main(String[] args) {
//        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        ApplicationContext app= new AnnotationConfigApplicationContext(springConfiguration.class);
        UserService userService = (UserService) app.getBean("userService");
        DataSource dataSource = (DataSource) app.getBean("dataSource");

        System.out.println(dataSource);
    }
}
