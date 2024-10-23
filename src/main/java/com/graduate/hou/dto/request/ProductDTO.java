package com.graduate.hou.dto.request;

import java.math.BigDecimal;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    
    private Long productId;

    private String name;

    private String description;

    private BigDecimal price;

    private Long categoryId;

    private boolean isServing;

    private boolean isHidden;
}
