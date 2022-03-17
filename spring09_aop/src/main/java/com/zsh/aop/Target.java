package com.zsh.aop;

import org.springframework.stereotype.Component;

public class Target implements TargetInterface {
    @Override
    public void save() {
        System.out.println("save running....");
//        int i= 10/0;
    }
}
