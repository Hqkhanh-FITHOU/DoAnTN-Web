package com.graduate.hou.service;


import com.graduate.hou.dto.request.OrderDTO;
import com.graduate.hou.dto.request.OrderDTO2;
import com.graduate.hou.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrder();
    List<Order> getPendingOrder();
    Order findOrderById(Long id);
    Order createOrder(OrderDTO orderDTO);
    Order createOrder2(OrderDTO2 orderDTO);
    boolean updateOrder(Long id, OrderDTO orderDTO);
    boolean deleteOrder(Long id);
    boolean confirmOrder(Long id);
}
