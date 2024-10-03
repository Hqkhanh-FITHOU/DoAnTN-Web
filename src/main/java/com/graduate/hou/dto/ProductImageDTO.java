package com.graduate.hou.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDTO {

    private Long imageId;

    private Long productId;

    private String pathString;

}
