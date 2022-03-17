package com.zsh.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsh.domain.Product;
import com.zsh.enums.ProductStatusEnum;
import com.zsh.enums.ResponseEnum;
import com.zsh.mapper.ProductMapper;
import com.zsh.service.IProductService;
import com.zsh.vo.ProductDetailVo;
import com.zsh.vo.ProductVo;
import com.zsh.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseVo<PageInfo> list(Integer categroyId, Integer pageNum, Integer pageSize) {
        HashSet<Integer> categoryIdSet = new HashSet<>();
        if(categroyId!=null) {
            categoryService.findSubCategoryId(categroyId, categoryIdSet);
            categoryIdSet.add(categroyId);
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);
        List<ProductVo> productVoList =new ArrayList<>();
        for (Product product : products) {
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(product,productVo);
            productVoList.add(productVo);
        }

        PageInfo pageInfo=new PageInfo(products);
        pageInfo.setList(productVoList);
        return ResponseVo.success(pageInfo);


    }

    @Override
    public ResponseVo<ProductDetailVo> detail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        //此处不推荐使用！=在售的形式 这种情况 你若讲条件改为促销 就会报错
        if(product.getStatus().equals(ProductStatusEnum.OFF_SALE.getCode())||product.getStatus().equals(ProductStatusEnum.DELETE.getCode())){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        ProductDetailVo productDetailVo=new ProductDetailVo();
        BeanUtils.copyProperties(product,productDetailVo);
        //敏感数据处理
        productDetailVo.setStock(productDetailVo.getStock()>100?100:productDetailVo.getStock());
        return ResponseVo.success(productDetailVo);
    }
}
