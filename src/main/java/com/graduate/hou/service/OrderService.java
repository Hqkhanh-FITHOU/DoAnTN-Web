package com.graduate.hou.service;

import com.graduate.hou.dto.NotificationDTO;
import com.graduate.hou.dto.OrderDTO;
import com.graduate.hou.entity.Notification;
import com.graduate.hou.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrder();
    Order createOrder(OrderDTO orderDTO);
    Order updateOrder(Long id, OrderDTO orderDTO);
    void deleteOrder(Long id);
}
