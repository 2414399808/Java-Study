package com.zsh.controller;

import com.github.pagehelper.PageInfo;
import com.zsh.consts.MallConst;
import com.zsh.domain.User;
import com.zsh.form.ShippingForm;
import com.zsh.service.Impl.ShippingServiceImpl;
import com.zsh.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("shippings")
public class ShippingController {


    @Autowired
    private ShippingServiceImpl shippingService;


    @PostMapping
    public ResponseVo add(@Valid @RequestBody ShippingForm shippingForm, HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<Map<String, Integer>> add = shippingService.add(user.getId(), shippingForm);
        return add;
    }

    @DeleteMapping("{shippingId}")
    public ResponseVo delete(@PathVariable Integer shippingId,HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo delete = shippingService.delete(user.getId(), shippingId);
        return delete;

    }

    @PostMapping("{shippingId}")
    public ResponseVo update(@Valid @RequestBody ShippingForm shippingForm, @PathVariable Integer shippingId,HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo update = shippingService.update(user.getId(), shippingId, shippingForm);
        return update;
    }

    @GetMapping
    public ResponseVo<PageInfo> get(HttpSession session,
                                    @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                    @RequestParam(required = false,defaultValue = "10") Integer pageSize
    ){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<PageInfo> list = shippingService.list(user.getId(), pageNum, pageSize);
        return list;
    }



}
