package com.zsh.controller;

import com.zsh.domain.Role;
import com.zsh.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @RequestMapping("/list")
    public ModelAndView list(ModelAndView modelAndView){
        List<Role> roleList = roleService.list();
//        System.out.println(roleList);
        modelAndView.setViewName("role-list");
        modelAndView.addObject("roleList",roleList);
        return modelAndView;
    }


    @RequestMapping("addRole")
    public String addRole(String roleName,String roleDesc){
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleDesc(roleDesc);
        roleService.addRole(role);
        return "redirect:/role/list";
    }
}
