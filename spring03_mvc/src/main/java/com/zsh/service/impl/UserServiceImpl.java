package com.zsh.service.impl;

import com.zsh.dao.UserDao;
import com.zsh.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    @Override
    public void save() {
        userDao.save();
    }

    public UserServiceImpl() {
    }

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
