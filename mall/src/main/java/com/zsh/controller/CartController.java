package com.zsh.controller;

import com.zsh.consts.MallConst;
import com.zsh.domain.User;
import com.zsh.form.CartAddForm;
import com.zsh.form.CartUpdateForm;
import com.zsh.service.Impl.CartServiceImpl;
import com.zsh.vo.CartVo;
import com.zsh.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("carts")
public class CartController {
    @Autowired
    private CartServiceImpl cartService;

    @PostMapping
    public ResponseVo<CartVo> add(@Valid  @RequestBody CartAddForm cartAddForm, HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<CartVo> responseVo = cartService.add(user.getId(), cartAddForm);
        return responseVo;
    }

    @GetMapping
    public ResponseVo<CartVo> list(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<CartVo> list = cartService.list(user.getId());
        return list;
    }

    @PutMapping("{productId}")
    public ResponseVo<CartVo> update(@Valid  @RequestBody CartUpdateForm cartUpdateForm, @PathVariable Integer productId, HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<CartVo> update = cartService.update(user.getId(), productId, cartUpdateForm);
        return update;
    }

    @DeleteMapping("{productId}")
    public ResponseVo<CartVo> delete( @PathVariable Integer productId, HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<CartVo> delete = cartService.delete(user.getId(), productId);
        return delete;
    }

    @PutMapping("selectAll")
    public ResponseVo<CartVo> selectAll( HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<CartVo> cartVoResponseVo = cartService.selectAll(user.getId());
        return cartVoResponseVo;
    }
    @PutMapping("unSelectAll")
    public ResponseVo<CartVo> unSelectAll( HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<CartVo> cartVoResponseVo = cartService.unSelectAll(user.getId());
        return cartVoResponseVo;
    }

    @GetMapping("products/sum")
    public ResponseVo<Integer> sum( HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<Integer> sum = cartService.sum(user.getId());
        return sum;
    }




}
