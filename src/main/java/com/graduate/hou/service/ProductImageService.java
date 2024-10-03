package com.graduate.hou.service;

import java.util.List;

import com.graduate.hou.dto.ProductImageDTO;
import com.graduate.hou.entity.ProductImage;

public interface ProductImageService {
    List<ProductImage> getAllProductImages();

    ProductImage saveProductImage(ProductImageDTO productImageDTO);

    ProductImage updateProductImage(Long id, ProductImageDTO productImageDTO);

    void deleteProductImage(Long id);
}
