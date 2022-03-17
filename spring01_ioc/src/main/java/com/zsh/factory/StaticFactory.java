package com.zsh.factory;

import com.zsh.dao.UserDao;
import com.zsh.dao.impl.UserDaoImpl;

public class StaticFactory {
    public static UserDao getUserDao(){
        return new UserDaoImpl();
    }
}
