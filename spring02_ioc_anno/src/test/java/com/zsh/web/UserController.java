package com.zsh.web;

import com.zsh.config.SpringConfigration;
import com.zsh.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserController {
    public static void main(String[] args) {
//        ApplicationContext app=new ClassPathXmlApplicationContext("ApplicationContext.xml");
        ApplicationContext app=new AnnotationConfigApplicationContext(SpringConfigration.class);
        UserService userService = app.getBean(UserService.class);
        userService.save();
    }
}
