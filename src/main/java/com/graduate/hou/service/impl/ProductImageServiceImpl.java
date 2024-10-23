package com.graduate.hou.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduate.hou.dto.request.ProductImageDTO;
import com.graduate.hou.entity.Product;
import com.graduate.hou.entity.ProductImage;
import com.graduate.hou.repository.ProductImageRepository;
import com.graduate.hou.repository.ProductRepository;
import com.graduate.hou.service.ProductImageService;

@Service
public class ProductImageServiceImpl implements ProductImageService{

    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductImage> getAllProductImages() {
        return productImageRepository.findAll();
    }

    @Override
    public ProductImage saveProductImage(ProductImageDTO productImageDTO) {
        ProductImage productImage = new ProductImage();
        Optional<Product> productOptional = productRepository.findById(productImageDTO.getProductId());
        productImage.setProduct(productOptional.get());
        productImage.setPathString(productImageDTO.getPathString());

        return productImageRepository.save(productImage);
    }

    @Override
    public ProductImage updateProductImage(Long id, ProductImageDTO productImageDTO) {
        Optional<ProductImage> productImageOptional = productImageRepository.findById(productImageDTO.getImageId());
        ProductImage productImage = productImageOptional.get();
        Optional<Product> productOptional = productRepository.findById(productImageDTO.getProductId());
        productImage.setProduct(productOptional.get());
        productImage.setPathString(productImageDTO.getPathString());

        return productImageRepository.save(productImage);
    }

    @Override
    public void deleteProductImage(Long id) {
        productImageRepository.deleteById(id);
    }

}
