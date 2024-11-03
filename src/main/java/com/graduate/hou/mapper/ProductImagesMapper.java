package com.graduate.hou.mapper;

import com.graduate.hou.dto.request.ProductImageDTO;
import com.graduate.hou.entity.ProductImage;

public class ProductImagesMapper {
    public static ProductImageDTO toDTO(ProductImage productImage){
        ProductImageDTO dto = new ProductImageDTO();
        dto.setImageId(productImage.getImageId());
        dto.setPathString(productImage.getPathString());
        dto.setProductId(productImage.getProduct().getProductId());
        return dto;
    }
}
