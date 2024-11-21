package com.graduate.hou.mapper;

import com.graduate.hou.dto.request.OrderDTO;
import com.graduate.hou.entity.Order;

public class OrderMapper {
    public static OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setUserId(order.getUser().getUserId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setPaymentId(order.getPayment().getPaymentId());
        return dto;
    }
}
