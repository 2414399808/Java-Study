package com.zsh.mapper;

import com.zsh.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    List<User> selectAll();
    void addUser(@Param("username") String username,@Param("password") String password);

    void save(User user);
}
