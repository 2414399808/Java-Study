package com.zsh.service.impl;

import com.zsh.dao.UserDao;
import com.zsh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;


    @Override
    public void save() {
        userDao.save();
    }
}
