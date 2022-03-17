package com.zsh.controller;

import com.github.pagehelper.PageInfo;
import com.zsh.service.Impl.ProductServiceImpl;
import com.zsh.vo.ProductDetailVo;
import com.zsh.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;

    @GetMapping
    public ResponseVo<PageInfo> list(@RequestParam(required = false) Integer categoryId,
                                     @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false,defaultValue = "10") Integer pageSize){

        return productService.list(categoryId,pageNum,pageSize);
    }


    @GetMapping("{productId}")
    public ResponseVo<ProductDetailVo> detail(@PathVariable Integer productId) {
        return productService.detail(productId);
    }

}
