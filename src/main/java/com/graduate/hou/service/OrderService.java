package com.graduate.hou.service;


import com.graduate.hou.dto.request.OrderDTO;
import com.graduate.hou.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrder();
    Order createOrder(OrderDTO orderDTO);
    Order updateOrder(Long id, OrderDTO orderDTO);
    void deleteOrder(Long id);
}
