package com.zsh.service;

import com.github.pagehelper.PageInfo;
import com.zsh.vo.ProductDetailVo;
import com.zsh.vo.ResponseVo;

public interface IProductService {

    ResponseVo<PageInfo> list(Integer categroyId, Integer pageNum, Integer pageSize);

    ResponseVo<ProductDetailVo> detail(Integer productId);


}
