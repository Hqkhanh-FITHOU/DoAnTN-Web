package com.graduate.hou.dto;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long reviewId;

    private Long productId;

    private Long userId;

    private Float rating;

    private String comment;

    private LocalDateTime createdAt;
}
