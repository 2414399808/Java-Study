package com.zsh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="user" ,method = RequestMethod.GET, params = {"username"})
public class UserController {


@RequestMapping("save")
    public String  save(){
    System.out.println("springmvc save");
    return "redirect:/jsp/success.jsp";
    }
}
