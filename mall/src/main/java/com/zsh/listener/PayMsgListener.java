package com.zsh.listener;

import com.alibaba.fastjson.JSON;
import com.zsh.domain.PayInfo;
import com.zsh.service.Impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 关于payIofo 正确方式 pay项目提供client。jar mall引用该jar包
 */
@Component
@RabbitListener(queues = "payNotify")
@Slf4j
public class PayMsgListener {
    @Autowired
    private OrderServiceImpl orderService;


    @RabbitHandler
    public void process(String msg){
        log.info("【接收到消息】 {}",msg);
        PayInfo payInfo = JSON.parseObject(msg, PayInfo.class);
        if(payInfo.getPlatformStatus().equals("SUCCESS"));{
            //修改订单里的状态
            orderService.paid(payInfo.getOrderNo());
        }
    }
}
