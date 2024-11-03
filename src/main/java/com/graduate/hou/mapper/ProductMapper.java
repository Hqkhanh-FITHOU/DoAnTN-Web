package com.graduate.hou.mapper;

import java.util.ArrayList;
import java.util.List;

import com.graduate.hou.dto.request.ProductDTO;
import com.graduate.hou.dto.request.ProductImageDTO;
import com.graduate.hou.entity.Product;
import com.graduate.hou.entity.ProductImage;

public class ProductMapper {
    public static ProductDTO toDTO(Product product){
        ProductDTO dto = new ProductDTO();
        List<ProductImageDTO> imagsDto = new ArrayList<>();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setCategoryId(product.getCategory().getCategoryId());
        dto.setIsHidden(product.isHidden());
        dto.setIsServing(product.isServing());
        dto.setImages(null);
        dto.setDescription(product.getDescription());
        for(ProductImage img : product.getProductImages()){
            imagsDto.add(ProductImagesMapper.toDTO(img));
        }
        dto.setImagesDTO(imagsDto);
        return dto;
    }
}
