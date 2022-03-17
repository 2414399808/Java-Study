package com.zsh.demo;

import com.zsh.dao.BrandDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class brandDaoDemo {
    public static void main(String[] args) {
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext1.xml");
        BrandDao brandDao = (BrandDao) app.getBean("brandDao");
        brandDao.save();
    }
}
