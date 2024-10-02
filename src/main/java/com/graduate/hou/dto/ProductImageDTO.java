package com.graduate.hou.dto;

import com.graduate.hou.entity.Product;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDTO {

    private Long imageId;

    private Product product;

    private String pathString;

}
