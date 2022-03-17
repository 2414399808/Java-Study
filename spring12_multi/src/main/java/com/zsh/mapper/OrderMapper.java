package com.zsh.mapper;

import com.zsh.domain.Order;

import java.util.List;

public interface OrderMapper {
    List<Order> selectAll();
}
