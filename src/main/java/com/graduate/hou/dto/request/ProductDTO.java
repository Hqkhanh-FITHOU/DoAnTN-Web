package com.graduate.hou.dto.request;

import java.util.List;
import java.math.BigDecimal;
import org.springframework.web.multipart.MultipartFile;
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

    private Boolean isServing;

    private Boolean isHidden;

    private List<MultipartFile> images;

    private List<ProductImageDTO> imagesDTO;
}
