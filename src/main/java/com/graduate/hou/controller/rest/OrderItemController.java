package com.graduate.hou.controller.rest;


import com.graduate.hou.dto.request.OrderItemDTO;
import com.graduate.hou.entity.OrderItem;
import com.graduate.hou.service.impl.OrderItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/orderItem")
public class OrderItemController {
    @Autowired
    private OrderItemServiceImpl orderItemService;

    @PostMapping
    OrderItem createOrderItem(@RequestBody OrderItemDTO orderItemDTO){
        return orderItemService.createOrderItem(orderItemDTO);
    }

    @GetMapping
    List<OrderItem> getAllOrderItem(){
        return orderItemService.getAllOrderItem();
    }

    @PutMapping("/{id}")
    OrderItem updateOrderItem (@PathVariable Long id, @RequestBody OrderItemDTO orderItemDTO){
        return orderItemService.updateOrderItem(id, orderItemDTO);
    }

    @DeleteMapping("/{id}")
    String deleteOrderItem(@PathVariable Long id){
        orderItemService.deleteOrderItem(id);
        return "Xóa thành công";
    }
}
