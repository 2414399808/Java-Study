package com.zsh.controller;

import com.alibaba.fastjson.JSON;
import com.zsh.domain.User;
import com.zsh.domain.VO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
//请求地址是http://localhost:8080/xxx/***
//@RequestMapping(value="/user",method = RequestMethod.GET,params = {"username"})
public class UserController {

    //请求地址是http://localhost:8080/quick
    //请求地址是http://localhost:8080/user/quick
    @RequestMapping("/quick")
    public String save(){
        System.out.println("Controller save running...");
        return "/success.jsp";
//        return "forword:/success.jsp";
//        return "redirect:/success.jsp";
    }


    @RequestMapping("/quick2")
    public ModelAndView save2(){
        System.out.println("Controller save2 running...");


        /*
        mode1 模型 作用封装数据
        view 视图 作用 展示数据
         */
       ModelAndView modelAndView=new ModelAndView();

       //设置模型数据
        modelAndView.addObject("name","zhangsan");
       //设置视图名称
        modelAndView.setViewName("success");
       return modelAndView;
    }
    @RequestMapping("/quick3")
    public ModelAndView save3(ModelAndView modelAndView){
        //设置模型数据
        modelAndView.addObject("name","lisi");
        //设置视图名称
        modelAndView.setViewName("success");
        return modelAndView;
    }
    @RequestMapping("/quick4")
    public String save4(Model model){

        //设置模型数据
        model.addAttribute("name","wangwu");

        return "success";
    }
    @RequestMapping("/quick5")
    public String save5(HttpServletRequest request){
        request.setAttribute("name","zhaoliu");

        return "success";
    }
    @RequestMapping("/quick6")
    public void save6(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("周思航");


    }
    @RequestMapping("/quick7")
    @ResponseBody//告知springmvc这里直接写出而不是视图转发
    public String save7(){
       return "周思航2";

    }
    @RequestMapping("/quick8")
    @ResponseBody//告知springmvc这里直接写出而不是视图转发
    public String save8(){
        User user=new User();

        String s = JSON.toJSONString(user);
        return s;
    }

    @RequestMapping("/quick9")
    @ResponseBody//告知springmvc这里直接写出而不是视图转发
    //期望springmvc自动将User转换成json格式的字符串
    public User save9(){
        User user=new User("张三",14);


        return user;
    }
    @RequestMapping("/quick10")
    @ResponseBody
    public void save10(String username, int age){
        System.out.println(username);
        System.out.println(age);
    }

    @RequestMapping("/quick11")
    @ResponseBody
    public void save11(User user){
        System.out.println(user);
    }


    @RequestMapping("/quick12")
    @ResponseBody
    public void save12(String[] strs){
        System.out.println(Arrays.toString(strs));
    }

    @RequestMapping("/quick13")
    @ResponseBody
    public void save13(VO vo){
        System.out.println(vo);
    }

    @RequestMapping("/quick14")
    @ResponseBody
    public void save14(@RequestBody List<User> userList){
        System.out.println(userList);
    }

    @RequestMapping("/quick15")
    @ResponseBody
    public void save15(@RequestParam(value = "name",required = false,defaultValue = "lll") String username){
        System.out.println(username);

    }

    //localhost:8080/user/quick16/zhangsan
    @RequestMapping("/quick16/{username}")
    @ResponseBody
    public void save16(@PathVariable("username") String username){
        System.out.println(username);

    }

    @RequestMapping("/quick17")
    @ResponseBody
    public void save17(Date date){

        System.out.println(date);

    }
    @RequestMapping("/quick18")
    @ResponseBody
    public void save18(HttpServletRequest request, HttpServletResponse response, HttpSession session){

        System.out.println(request);
        System.out.println(response);
        System.out.println(session);

    }
    @RequestMapping("/quick19")
    @ResponseBody
    public void save19(@RequestHeader(value = "User-Agent",required = false,defaultValue = "lll") String user_agent){
        System.out.println(user_agent);

    }
    @RequestMapping("/quick20")
    @ResponseBody
    public void save20(@CookieValue(value = "JSESSIONID") String jessionid){

        System.out.println(jessionid);

    }
    @RequestMapping("/quick21")
    @ResponseBody
    public void save21(String username, MultipartFile uploadFile) throws IOException {
        System.out.println(username);
        System.out.println(uploadFile);
        //获得上传文件的名称
        String originalFilename = uploadFile.getOriginalFilename();
        uploadFile.transferTo(new File("E:\\javastudy\\spring\\spring03_mvc\\src\\main\\java\\com\\zsh/"+originalFilename));

    }
}
