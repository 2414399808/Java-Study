package com.zsh.controller;

import com.zsh.domain.Role;
import com.zsh.domain.User;
import com.zsh.service.RoleService;
import com.zsh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @RequestMapping("/list")
    public ModelAndView list(ModelAndView modelAndView){
        List<User> userList = userService.list();
        System.out.println(userList);
        modelAndView.addObject("userList",userList);
        modelAndView.setViewName("user-list");
        return modelAndView;
    }

    @RequestMapping("/roleList")
    public ModelAndView roleList(ModelAndView modelAndView){
        List<Role> roleList = roleService.roleList();
        modelAndView.addObject("roleList",roleList);
        modelAndView.setViewName("user-add");
        return modelAndView;
    }
    @RequestMapping("/addUser")
    public String addUser(User user,Long[] roleId){
        userService.addUser(user,roleId);
        return "redirect:/user/list";
    }
    @RequestMapping("/login")
    public  String login(String username, String password, HttpSession session){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User login = userService.login(user);
        session.setAttribute("user",login);
        if(login==null){
            return "redirect:/login.jsp";
        }

        return "redirect:/index.jsp";

    }
}
