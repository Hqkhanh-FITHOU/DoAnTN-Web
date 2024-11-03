package com.graduate.hou.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduate.hou.dto.request.ProductDTO;
import com.graduate.hou.entity.Category;
import com.graduate.hou.entity.Product;
import com.graduate.hou.repository.CategoryRepository;
import com.graduate.hou.repository.ProductRepository;
import com.graduate.hou.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());
        product.setCategory(category.get());
        product.setName(productDTO.getName());
        product.setHidden(productDTO.getIsHidden());
        product.setServing(productDTO.getIsServing());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        log.info("ProductService: [creat product]");
        return productRepository.save(product);
    }

    @Override
    public boolean updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> product = productRepository.findById(id);
        Product productUpdate = product.get();
        Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());
        productUpdate.setCategory(category.get());
        productUpdate.setName(productDTO.getName());
        productUpdate.setHidden(productDTO.getIsHidden());
        productUpdate.setServing(productDTO.getIsServing());
        productUpdate.setPrice(productDTO.getPrice());
        productUpdate.setDescription(productDTO.getDescription());
        try {
            productRepository.save(productUpdate);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product findProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.get();
    }

}
