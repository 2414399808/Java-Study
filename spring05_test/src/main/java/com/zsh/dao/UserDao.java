package com.zsh.dao;

import com.zsh.domain.User;

import java.util.List;

public interface UserDao {
    List<User> list();
    List<User> findAll();
    Long addUser(User user);
    User login(User user);
    void saveUserRoleRel(Long id, Long[] ids);
}
