package com.graduate.hou.controller.rest;

import com.graduate.hou.dto.request.OrderDTO;
import com.graduate.hou.entity.Order;
import com.graduate.hou.entity.Payment;
import com.graduate.hou.entity.User;
import com.graduate.hou.enums.OrderStatus;
import com.graduate.hou.mapper.OrderMapper;
import com.graduate.hou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;




@Validated
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;


    @PostMapping("/add")
    public Order createOrder(@RequestBody OrderDTO orderDTO){
        return orderService.createOrder(orderDTO);
    }

    @GetMapping("/all")
    public List<Order> getAllOrder(){
        return orderService.getAllOrder();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") Long id) {
        Order order = orderService.findOrderById(id);
        
        return ResponseEntity.ok(OrderMapper.toDTO(order));
    }

    @GetMapping("/{id}/payment")
    public ResponseEntity<Payment> getPaymentByOrderId(@PathVariable("id") Long id) {
        Order order = orderService.findOrderById(id);
        return ResponseEntity.ok(order.getPayment());
    }

    @GetMapping("/{orderId}/confirmDelivery/{id}")
    public ResponseEntity<Boolean> getOrderById(@PathVariable("orderId") Long orderId, @PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.saveOrderWithDelivery(orderId, id));
    }

    @GetMapping("/{orderId}/confirmComplete")
    public ResponseEntity<Boolean> confirmCompleteOrder(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(orderService.confirmCompleteOrder(orderId));
    }
    

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserByOrderId(@PathVariable("id") Long id) {
        Order order = orderService.findOrderById(id);
        User user = order.getUser();
        return ResponseEntity.ok(user);
    }
    

    @GetMapping("/comming")
    public List<OrderDTO> getCommingOrder(){
        List<OrderStatus> statuses = List.of(
            OrderStatus.PENDING,
            OrderStatus.ON_DELIVERY,
            OrderStatus.ON_PROGRESS,
            OrderStatus.WILLING_DELIVERY
        );
        
        return orderService.getOrderDtoByStatusList(statuses);
    }

    @GetMapping("/quantity/{id}")
    public int getTotalQuantity(@PathVariable("id") Long id) {
        Order order = orderService.findOrderById(id);
        return order.getTotalQuantity();
    }
    

    @GetMapping("/coming/{id}")
    public List<OrderDTO> getCommingOrderForUserId(@PathVariable("id") Long id) {
        List<OrderStatus> statuses = List.of(
            OrderStatus.PENDING,
            OrderStatus.ON_DELIVERY,
            OrderStatus.ON_PROGRESS,
            OrderStatus.WILLING_DELIVERY
        );
        return orderService.getOrderDtoByStatusesAndUserId(statuses, id);
    }

    @GetMapping("/completed/{id}")
    public List<OrderDTO> getCompletedOrderForUserId(@PathVariable("id") Long id) {
        List<OrderStatus> statuses = List.of(
            OrderStatus.COMPLETED
        );
        return orderService.getOrderDtoByStatusesAndUserId(statuses, id);
    }

    @GetMapping("/canceled/{id}")
    public List<OrderDTO> getCanceledOrderForUserId(@PathVariable("id") Long id) {
        List<OrderStatus> statuses = List.of(
            OrderStatus.CANCELED
        );
        return orderService.getOrderDtoByStatusesAndUserId(statuses, id);
    }
    
    @GetMapping("/delivering/{id}")
    public List<OrderDTO> getDeliveringOrderForDeliveryId(@PathVariable("id") Long id) {
        List<OrderStatus> statuses = List.of(
            OrderStatus.ON_DELIVERY
        );
        return orderService.getOrderDtoByStatusesAndDeliveryId(statuses, id);
    }

    @GetMapping("/willing-delivery")
    public List<OrderDTO> getWillingDeliveryOrder() {
        List<OrderStatus> statuses = List.of(
            OrderStatus.WILLING_DELIVERY
        );
        return orderService.getOrderDtoByStatusList(statuses);
    }

    // @PutMapping("update/{id}")
    // Order updateOrder (@PathVariable Long id, @RequestBody OrderDTO orderDTO){
    //     return orderService.updateOrder(id, orderDTO);
    // }

    @DeleteMapping("delete/{id}")
    public String deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return "Xóa thành công";
    }
}
