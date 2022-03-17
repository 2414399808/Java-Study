package com.zsh.service;

import com.zsh.config.WxAccountConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class test {

    @Autowired
    private WxAccountConfig wxAccountConfig;

    @Test
    public void test(){

        System.out.println(wxAccountConfig.getAppId());
        System.out.println(wxAccountConfig.getMchId());
        System.out.println(wxAccountConfig.getMchKey());
        System.out.println(wxAccountConfig.getNotifyUrl());
        System.out.println(wxAccountConfig.getReturnUrl());
    }

}
