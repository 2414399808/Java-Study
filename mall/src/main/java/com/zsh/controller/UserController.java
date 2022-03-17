package com.zsh.controller;

import com.zsh.consts.MallConst;
import com.zsh.domain.User;
import com.zsh.form.UserLoginForm;
import com.zsh.form.UserRegisterForm;
import com.zsh.service.Impl.UserServiceImpl;
import com.zsh.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
    @Autowired
    private UserServiceImpl userService;



    @PostMapping("register")
    public ResponseVo<User> register(@Valid @RequestBody UserRegisterForm userForm
           // , BindingResult bindingResult
    ){
//        if(bindingResult.hasErrors()){
//            return ResponseVo.error(ResponseEnum.PARAM_ERROR,bindingResult);
//        }
            User user=new User();
        BeanUtils.copyProperties(userForm,user);

        return userService.register(user);
    }


    @PostMapping("login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                              //    BindingResult bindingResult,
                                  HttpSession session){

//        if(bindingResult.hasErrors()){
//            return ResponseVo.error(ResponseEnum.PARAM_ERROR,bindingResult);
//        }

        ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());
        //设置session
        session.setAttribute(MallConst.CURRENT_USER,userResponseVo.getData());
        return userResponseVo;
    }

    //session 保存在内存里 token+  redis
    @GetMapping
    public ResponseVo<User> userInfo(HttpSession session){
        log.info("/user sessionId={}",session.getId());
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        return ResponseVo.success(user);
    }
    //TODO 判断登录状态，拦截器
    @PostMapping("logout")
    public ResponseVo logout(HttpSession session){
        log.info("/user/logout sessionId={}",session.getId());

        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success();

    }

}
