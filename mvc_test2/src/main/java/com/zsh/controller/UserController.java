package com.zsh.controller;

import com.zsh.domian.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("user")
public class UserController {


    @RequestMapping("save")
    public String save(){
        return "/success.jsp";
    }
    @RequestMapping("save2")
    public ModelAndView save2(ModelAndView modelAndView){
       modelAndView.addObject("zsh","zsh");
       modelAndView.setViewName("/success.jsp");
       return modelAndView;
    }

    @RequestMapping("save3")
    public String save3(Model model){
       model.addAttribute("zsh","zsh");
        return "/success.jsp";
    }


    @RequestMapping("save4")
    @ResponseBody
    public String save4(){

        return "/success.jsp";
    }
    @RequestMapping("save5")
    @ResponseBody
    public User save5(){

      User user =new User("zsh","88");
      return  user;
    }

}
