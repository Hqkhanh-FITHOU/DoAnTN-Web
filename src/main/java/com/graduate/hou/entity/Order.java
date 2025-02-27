package com.graduate.hou.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.graduate.hou.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_delivery_id", nullable = true)
    @JsonIgnore
    private User delivery;

    @Column(nullable = true, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = true, precision = 10, scale = 2)
    private BigDecimal totalDiscount;

    @Column(nullable = true, precision = 10, scale = 2)
    private BigDecimal totalPayment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private String note;

    @Column(nullable = true)
    private String cancelReason;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false)
    @JsonIgnore
    private Payment payment;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<OrderItem> orderItems;

    public int getTotalQuantity() {
        if (orderItems == null) {
            return 0;
        }
        return orderItems.stream().mapToInt(OrderItem::getQuantity).sum();
    }
}
