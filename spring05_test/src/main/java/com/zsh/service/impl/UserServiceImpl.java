package com.zsh.service.impl;

import com.zsh.dao.UserDao;
import com.zsh.domain.User;
import com.zsh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;
    @Override
    public List<User> list() {
        List<User> userList = userDao.list();
        return userList;
    }

    @Override
    public void addUser(User user,Long[] ids) {
        Long userId = userDao.addUser(user);
        userDao.saveUserRoleRel(userId,ids);
    }

    @Override
    public User login(User user) {
        User login = null;
        try {
            login = userDao.login(user);
        } catch (Exception e) {
            return null;
        }
        return login;
    }
}
