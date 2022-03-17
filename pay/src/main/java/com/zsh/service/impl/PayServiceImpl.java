package com.zsh.service.impl;

import com.alibaba.fastjson.JSON;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.zsh.domain.PayInfo;
import com.zsh.enums.PayPlatformEnum;
import com.zsh.mapper.PayInfoMapper;
import com.zsh.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class PayServiceImpl implements IPayService {


    private final static String QUEUE_PAY_NOTIFY="payNotify";

    @Autowired
    private PayInfoMapper payInfoMapper;


    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private AmqpTemplate amqpTemplatel;

    @Override
    public PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum) {
        //写入数据库
        PayInfo payInfo = new PayInfo(Long.parseLong(orderId),
                PayPlatformEnum.getByBestPayType(bestPayTypeEnum).getCode(),
                OrderStatusEnum.NOTPAY.name(),
                amount);

        payInfoMapper.insertSelective(payInfo);
        PayRequest request = new PayRequest();
        request.setOrderName("zsh2414-最好的支付sdk");
        request.setOrderId(orderId);
        request.setOrderAmount(amount.doubleValue());
        request.setPayTypeEnum(bestPayTypeEnum);
        PayResponse response = bestPayService.pay(request);
        log.info("response={}", response);

        return response;
    }

    @Override
    public String asyncNotify(String notifyData) {
        //1 签名校验
        PayResponse response = bestPayService.asyncNotify(notifyData);
        log.info("payRespnse={}", response);

        //2 金额校验(从数据库查订单)
        //比较严重，正常情况不会发生 发出告警
        PayInfo payInfo = payInfoMapper.selectByOrderNo(Long.parseLong(response.getOrderId()));
        if (payInfo == null) {
            throw new RuntimeException("通过orderNO查询到的结果是null");
        }
        //如果订单支付状态不是 已支付

            if (!payInfo.getPlatformStatus().equals(OrderStatusEnum.SUCCESS.name())) {
                if (payInfo.getPayAmount().compareTo(BigDecimal.valueOf(response.getOrderAmount())) != 0) {
                    //告警
                    throw new RuntimeException("异步通知中的金额和数据库里的不一致  orderNo=" + response.getOrderId());
                }


            }
                //3 修改订单支付状态
                payInfo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());
            payInfo.setPlatformNumber(response.getOutTradeNo());
            //让数据库自己更新
            payInfo.setUpdateTime(null);
                payInfoMapper.updateByPrimaryKeySelective(payInfo);


                //TODO pay发送MQ消息，mall接收MQ消息
        String pi = JSON.toJSONString(payInfo);
        amqpTemplatel.convertAndSend(QUEUE_PAY_NOTIFY,pi);
            if (response.getPayPlatformEnum() == BestPayPlatformEnum.WX) {

                //4 告诉微信不要在通知了
                return "<xml>\n" +
                        " <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                        " <return_msg><![CDATA[OK]]></return_msg>\n" +
                        "</xml>";
            } else if (response.getPayPlatformEnum() == BestPayPlatformEnum.ALIPAY) {
                return "success";
            }


            throw new RuntimeException("异步通知中错误的支付平台");

    }

    @Override
    public PayInfo queryByOrderId(String orderId) {
        return payInfoMapper.selectByOrderNo(Long.parseLong(orderId));
    }

}

