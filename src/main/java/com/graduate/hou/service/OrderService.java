package com.graduate.hou.service;


import com.graduate.hou.dto.request.OrderDTO;
import com.graduate.hou.dto.request.OrderDTO2;
import com.graduate.hou.entity.Order;
import com.graduate.hou.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrder();
    List<Order> getPendingOrder();
    List<Order> getOrderByStatusList(List<OrderStatus> list);
    List<OrderDTO> getOrderDtoByStatusList(List<OrderStatus> list);
    List<OrderDTO> getOrderDtoByStatusesAndUserId(List<OrderStatus> list, Long userId);
    List<OrderDTO> getOrderDtoByStatusesAndDeliveryId(List<OrderStatus> list, Long userId);
    Order findOrderById(Long id);
    Order createOrder(OrderDTO orderDTO);
    Order createOrder2(OrderDTO2 orderDTO);
    boolean updateOrder(Long id, OrderDTO orderDTO);
    boolean updateCancelReason(Long id, String cancelReason);
    boolean deleteOrder(Long id);
    boolean confirmOrder(Long id);
    boolean confirmCompleteOrder(Long id);
    boolean updateState(Long id, OrderStatus status);
    boolean saveOrderWithDelivery(Long orderId, Long userId);
}
