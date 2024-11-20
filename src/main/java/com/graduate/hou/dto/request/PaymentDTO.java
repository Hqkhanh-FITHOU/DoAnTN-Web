package com.graduate.hou.dto.request;


import com.graduate.hou.enums.PaymentMethod;
import com.graduate.hou.enums.PaymentStatus;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Long paymentId;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private LocalDateTime paymentDate;
}
