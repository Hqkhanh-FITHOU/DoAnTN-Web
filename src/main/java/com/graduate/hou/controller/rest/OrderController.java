package com.graduate.hou.controller.rest;

import com.graduate.hou.dto.request.OrderDTO;
import com.graduate.hou.entity.Order;
import com.graduate.hou.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Validated
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping
    Order createOrder(@RequestBody OrderDTO orderDTO){
        return orderService.createOrder(orderDTO);
    }

    @GetMapping
    List<Order> getAllOrder(){
        return orderService.getAllOrder();
    }

    @PutMapping("/{id}")
    Order updateOrder (@PathVariable Long id, @RequestBody OrderDTO orderDTO){
        return orderService.updateOrder(id, orderDTO);
    }

    @DeleteMapping("/{id}")
    String deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return "Xóa thành công";
    }
}
