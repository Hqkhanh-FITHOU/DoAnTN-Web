package com.graduate.hou.service;


import com.graduate.hou.dto.OrderItemDTO;
import com.graduate.hou.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> getAllOrderItem();
    OrderItem createOrderItem(OrderItemDTO orderItemDTO);
    OrderItem updateOrderItem(Long id, OrderItemDTO orderItemDTO);
    void deleteOrderItem(Long id);
}
