package com.zsh.annoaop;

import org.springframework.stereotype.Component;

@Component("target")
public class Target implements TargetInterface{

    public void save(){
        System.out.println(" target save......");
    }


}
