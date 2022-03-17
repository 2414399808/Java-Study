package com.zsh.service.Impl;

import com.alibaba.fastjson.JSON;
import com.zsh.domain.Cart;
import com.zsh.domain.Product;
import com.zsh.enums.ProductStatusEnum;
import com.zsh.enums.ResponseEnum;
import com.zsh.form.CartAddForm;
import com.zsh.form.CartUpdateForm;
import com.zsh.mapper.ProductMapper;
import com.zsh.service.ICartService;
import com.zsh.vo.CartProductVo;
import com.zsh.vo.CartVo;
import com.zsh.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private ProductMapper productMapper;

    private final static String CART_REDIS_KEY_TEMPLATE="cart_%d";

    Integer quantity = 1;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ResponseVo<CartVo> add(Integer uid,CartAddForm form) {

        Product product = productMapper.selectByPrimaryKey(form.getProductId());
        //商品是否存在
        if(product==null){
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }
        //商品是否正常在售
        if(!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        //商品库存是否充足
        if(product.getStock()<=0){
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }
        //写入到redis
        //key: cart_UID
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String value = opsForHash.get(String.format(CART_REDIS_KEY_TEMPLATE, uid), product.getId().toString());
        Cart cart=new Cart();
        if(value==null){
            //没有购买该商品
            cart=new Cart(form.getProductId(), quantity, form.getSelected());
        }else{
            //已经有了数量加一
             cart = JSON.parseObject(value, Cart.class);
            cart.setQuantity(cart.getQuantity()+quantity);
        }
        opsForHash.put(String.format(CART_REDIS_KEY_TEMPLATE, uid),String.valueOf(product.getId())
                , JSON.toJSONString(cart));


        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        Map<String, String> entries = opsForHash.entries(String.format(CART_REDIS_KEY_TEMPLATE, uid));

        boolean selectAll = true;

        Integer cartTotalQuantity= 0;

        BigDecimal cartTotalPrice=BigDecimal.ZERO;

        Set<String> keys = entries.keySet();
        List<Product> products = productMapper.selectByKeySet(keys);
        List<CartProductVo> cartProductVoList=new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer key = Integer.valueOf(entry.getKey());
            Cart cart = JSON.parseObject(entry.getValue(), Cart.class);
            //TODO 需要优化 使用mysql里的in
//          Product product = productMapper.selectByPrimaryKey(key);
            Product product=new Product();
            for (Product product1 : products) {
                if (product1.getId().equals(key)){
                    product=product1;
                }
            }
            if(product !=null){
                CartProductVo cartProductVo = new CartProductVo(key
                        ,cart.getQuantity()
                        ,product.getName()
                        ,product.getSubtitle()
                        ,product.getMainImage()
                        ,product.getPrice()
                        ,product.getStatus()
                        ,product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()))
                        ,product.getStock(),
                        cart.getProductSelected());
                cartProductVoList.add(cartProductVo);
                if(!cart.getProductSelected()){
                    selectAll=false;
                }else {
                    cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                }
            }
                cartTotalQuantity+=cart.getQuantity();

        }
            CartVo cartVo = new CartVo();
            cartVo.setSelectedAll(selectAll);
            cartVo.setCartTotalPrice(cartTotalPrice);
            cartVo.setCartTotalQuantity(cartTotalQuantity);
            cartVo.setCartProductVOList(cartProductVoList);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String value = opsForHash.get(String.format(CART_REDIS_KEY_TEMPLATE,uid), productId.toString());
        if(value==null){
            //没有该商品 报错
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        Cart cart = JSON.parseObject(value, Cart.class);
        if(cartUpdateForm.getQuantity()>=0&&cartUpdateForm.getQuantity()!=null){
        cart.setQuantity(cartUpdateForm.getQuantity());}
        if(cartUpdateForm.getSelected()!=null){
        cart.setProductSelected(cartUpdateForm.getSelected());}
        opsForHash.put(String.format(CART_REDIS_KEY_TEMPLATE,uid),productId.toString(),JSON.toJSONString(cart));

        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String value = opsForHash.get(String.format(CART_REDIS_KEY_TEMPLATE,uid), productId.toString());
        if(value==null){
            //没有该商品 报错
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }

        opsForHash.delete(String.format(CART_REDIS_KEY_TEMPLATE,uid),productId.toString());
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        List<String> values = opsForHash.values(String.format(CART_REDIS_KEY_TEMPLATE, uid));
        for (String value : values) {
            Cart cart = JSON.parseObject(value, Cart.class);
            cart.setProductSelected(true);
            String s = JSON.toJSONString(cart);
            opsForHash.put(String.format(CART_REDIS_KEY_TEMPLATE,uid),cart.getProductId().toString(),s);
        }
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        List<String> values = opsForHash.values(String.format(CART_REDIS_KEY_TEMPLATE, uid));
        for (String value : values) {
            Cart cart = JSON.parseObject(value, Cart.class);
            cart.setProductSelected(false);
            String s = JSON.toJSONString(cart);
            opsForHash.put(String.format(CART_REDIS_KEY_TEMPLATE,uid),cart.getProductId().toString(),s);
        }
        return list(uid);
    }

    @Override
    public ResponseVo<Integer> sum(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        List<String> values = opsForHash.values(String.format(CART_REDIS_KEY_TEMPLATE, uid));
        Integer cartTotalQuantity=0;
        for (String value : values) {
            Cart cart = JSON.parseObject(value, Cart.class);
            cartTotalQuantity+=cart.getQuantity();
        }
        return ResponseVo.success(cartTotalQuantity);
    }

    @Override
    public List<Cart> listCart(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        List<String> values = opsForHash.values(String.format(CART_REDIS_KEY_TEMPLATE, uid));
        List<Cart> cartList =new ArrayList<>();
        for (String value : values) {
            Cart cart = JSON.parseObject(value, Cart.class);
            cartList.add(cart);
        }
        return cartList;
    }




}
