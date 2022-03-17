package com.zsh.service;

import com.zsh.domain.User;
import com.zsh.vo.ResponseVo;

public interface IUserService {


    /**
     * 注册功能
     * @param user
     * @return
     */
    ResponseVo register(User user);

    /**
     * 登录功能
     */

    ResponseVo<User> login(String username,String password);
}
