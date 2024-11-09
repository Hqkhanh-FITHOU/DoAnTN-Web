package com.graduate.hou.service;


import com.graduate.hou.dto.request.OrderDTO;
import com.graduate.hou.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrder();
    Order findOrderById(Long id);
    Order createOrder(OrderDTO orderDTO);
    boolean updateOrder(Long id, OrderDTO orderDTO);
    boolean deleteOrder(Long id);
}
