package com.zsh.mapper;

import com.zsh.domain.User;

import java.util.List;

public interface UserMapper {
    List<User> selectAll();

    List<User> findUserAndRole();
}
