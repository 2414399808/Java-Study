package com.zsh.mappertest;

import com.zsh.domain.Order;
import com.zsh.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderMapperTest {
    @Autowired
    private OrderMapper orderMapper;


    @Test
    public void testOrder(){
        Order order = orderMapper.selectByPrimaryKey(1);
        System.out.println(order);
    }
}
