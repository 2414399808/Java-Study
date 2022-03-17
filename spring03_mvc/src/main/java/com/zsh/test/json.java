package com.zsh.test;

import com.alibaba.fastjson.JSON;
import com.zsh.domain.User;

import java.util.ArrayList;
import java.util.List;

public class json {
    public static void main(String[] args) {
        List<User> userlist=new ArrayList<>();
        userlist.add(new User("zhangsan",23));
        userlist.add(new User("lisi",23));
        String s = JSON.toJSONString(userlist);
        System.out.println(s);

    }
}
