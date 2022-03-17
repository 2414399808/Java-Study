package com.zsh.controller;

import com.github.pagehelper.PageInfo;
import com.zsh.consts.MallConst;
import com.zsh.domain.User;
import com.zsh.form.OrderCreateForm;
import com.zsh.service.Impl.OrderServiceImpl;
import com.zsh.vo.OrderVo;
import com.zsh.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping()
    public ResponseVo<OrderVo> create(@Valid @RequestBody OrderCreateForm orderCreateForm, HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<OrderVo> orderVoResponseVo = orderService.create(user.getId(), orderCreateForm.getShippingId());
        return orderVoResponseVo;


    }

    @GetMapping
    public ResponseVo<PageInfo> list(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                                     HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<PageInfo> list = orderService.list(user.getId(), pageNum, pageSize);
        return list;
    }


    @GetMapping("{orderNo}")
    public ResponseVo<OrderVo> detail( @PathVariable Long orderNo, HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<OrderVo> detail = orderService.detail(user.getId(), orderNo);
        return detail;
    }



    @PutMapping("{orderNo}")
    public ResponseVo cancel(@PathVariable Long orderNo,HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo cancel = orderService.cancel(user.getId(),orderNo);
        return cancel;

    }
}
