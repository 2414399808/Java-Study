package com.zsh.service;

import com.zsh.dao.UserDao;
import com.zsh.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

   List<User> list();
   void addUser(User user,Long[] roleIds);
   User login(User user);
}
