package com.zsh.mappertest;

import com.zsh.domain.Product;
import com.zsh.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
public class ProductMapperTest {
    @Autowired
    private ProductMapper productMapper;



    @Test
    public void test2(){
        HashSet<String> strings = new HashSet<>();
        Collections.addAll(strings,"26","27");
        List<Product> products = productMapper.selectByKeySet(strings);
        System.out.println(products);
    }
}
