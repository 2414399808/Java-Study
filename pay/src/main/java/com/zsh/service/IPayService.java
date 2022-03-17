package com.zsh.service;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import com.zsh.domain.PayInfo;

import java.math.BigDecimal;

public interface IPayService {


    /**
     * 创建/发起支付
     * @param OrderId
     * @param amount
     * @return
     */
    PayResponse create(String OrderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum);

    /**
     * 异步通知
     */


    String asyncNotify(String notifyData);

    /**
     * 查询支付记录（通过订单号
     * @param orderId
     * @return
     */
    PayInfo queryByOrderId(String orderId);
}
