package com.zsh.demo;

import com.zsh.service.BrandService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BrandController {
    @Test
    public void test(){
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext1.xml");
        BrandService brandService = (BrandService) app.getBean("brandService");
        brandService.save();
    }
}
