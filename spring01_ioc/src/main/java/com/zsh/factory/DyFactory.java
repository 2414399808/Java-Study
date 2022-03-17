package com.zsh.factory;

import com.zsh.dao.UserDao;
import com.zsh.dao.impl.UserDaoImpl;

public class DyFactory {
    public UserDao getUserDao(){
        return new UserDaoImpl();
    }
}
