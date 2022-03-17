package com.zsh.service.Impl;

import com.zsh.domain.User;
import com.zsh.enums.ResponseEnum;
import com.zsh.enums.RoleEnum;
import com.zsh.mapper.UserMapper;
import com.zsh.service.IUserService;
import com.zsh.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVo<User> register(User user) {
        //username 不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if(countByUsername>0){
           return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }
        //email不能重复
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if(countByEmail>0){
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
        }

        //MD5 摘要算法(spring自带)
        String psw = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(psw);

        user.setRole(RoleEnum.CUSTOMER.getCode());


        //写入数据库
        int resultCount = userMapper.insertSelective(user);
        if(resultCount==0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }

     return ResponseVo.success();

    }

    @Override
    public ResponseVo<User> login(String username,String password) {
        User user = userMapper.selectByUsername(username);
        if(user==null){
            //用户不存在
            //不能提示用户名不存在，为了数据库的安全 一般提醒用户名或密码错误
            return  ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        if (!user.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
            //密码错误
            return  ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        //用户名或密码错误
        user.setPassword(null);
        return ResponseVo.success(user);
    }

    private void error(){
        throw new RuntimeException("意外错误");
    }
}
