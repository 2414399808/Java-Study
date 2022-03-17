package com.zsh.domain;

import java.util.List;

public class VO {
    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    @Override
    public String toString() {
        return "VO{" +
                "userList=" + userList +
                '}';
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
