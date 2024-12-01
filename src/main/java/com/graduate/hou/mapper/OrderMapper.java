package com.graduate.hou.mapper;

import com.graduate.hou.dto.request.OrderDTO;
import com.graduate.hou.entity.Order;

public class OrderMapper {
    public static OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setDeliveryId(order.getDelivery() != null ? order.getDelivery().getUserId() : null);
        dto.setUserId(order.getUser().getUserId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setTotalDiscount(order.getTotalDiscount());
        dto.setTotalPayment(order.getTotalPayment());
        dto.setStatus(order.getStatus());
        dto.setPaymentId(order.getPayment().getPaymentId());
        dto.setCreatedDate(order.getCreatedAt());
        dto.setAddress(order.getAddress());
        dto.setNote(order.getNote());
        dto.setCancelReason(order.getCancelReason());
        dto.setUpdatedDate(order.getUpdatedAt());
        return dto;
    }
}
