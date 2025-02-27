package com.graduate.hou.service.impl;

import com.graduate.hou.dto.request.OrderItemDTO;
import com.graduate.hou.entity.Order;
import com.graduate.hou.entity.OrderItem;
import com.graduate.hou.entity.Product;
import com.graduate.hou.repository.OrderItemRepository;
import com.graduate.hou.repository.OrderRepository;
import com.graduate.hou.repository.ProductRepository;
import com.graduate.hou.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<OrderItem> getAllOrderItem() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem createOrderItem(OrderItemDTO orderItemDTO) {
        Order order = orderRepository.findById(orderItemDTO.getOrderId())
                .orElseThrow(()-> new RuntimeException("Mã order không tồn tại"));

        Product product = productRepository.findById(orderItemDTO.getProductId())
                .orElseThrow(()-> new RuntimeException("Sản phẩm không tồn tại"));

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(orderItemDTO.getQuantity())
                .priceAtOrderTime(orderItemDTO.getPriceAtOrderTime())
                .build();

        return orderItemRepository.save(orderItem);
    }

    @SuppressWarnings("static-access")
    @Override
    public OrderItem updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
        Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(id);

        Order order = orderRepository.findById(orderItemDTO.getOrderId())
                .orElseThrow(()-> new RuntimeException("Mã order không tồn tại"));

        Product product = productRepository.findById(orderItemDTO.getProductId())
                .orElseThrow(()-> new RuntimeException("Sản phẩm không tồn tại"));

        OrderItem orderItem = optionalOrderItem.get().builder()
                .order(order)
                .product(product)
                .quantity(orderItemDTO.getQuantity())
                .priceAtOrderTime(orderItemDTO.getPriceAtOrderTime())
                .build();

        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

@Override
public List<OrderItem> getOrderItemsByOrderId(Long id) {
    return orderItemRepository.findByOrder_OrderId(id);
}
}
