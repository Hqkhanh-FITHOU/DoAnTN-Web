package com.graduate.hou.repository;

import com.graduate.hou.entity.Order;
import com.graduate.hou.enums.OrderStatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o ORDER BY o.id DESC")
    List<Order> findAllOrdersSortedByIdDesc();

    @Query("SELECT o FROM Order o WHERE o.status = :status ORDER BY o.id DESC")
    List<Order> findByStatusAndIdDesc(OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.status IN :statusList ORDER BY o.id DESC")
    List<Order> findByStatusInAndIdDesc(List<OrderStatus> statusList);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.status IN :statuses ORDER BY o.id DESC")
    List<Order> findOrdersByUserAndStatuses(@Param("userId") Long userId, @Param("statuses") List<OrderStatus> statuses);

    @Query("SELECT o FROM Order o WHERE o.delivery.id = :deliveryId AND o.status IN :statuses ORDER BY o.id DESC")
    List<Order> findOrdersByDeliveryAndStatuses(@Param("deliveryId") Long deliveryId, @Param("statuses") List<OrderStatus> statuses);
}
