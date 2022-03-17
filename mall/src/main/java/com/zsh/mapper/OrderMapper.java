package com.zsh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsh.domain.Order;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> selectByUid(Integer uid);

    Order selectByOrderNo(Long orderNo);
}