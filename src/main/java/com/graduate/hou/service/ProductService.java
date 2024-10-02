package com.graduate.hou.service;

import java.util.List;
import com.graduate.hou.dto.ProductDTO;
import com.graduate.hou.entity.Product;

public interface ProductService {
    
    List<Product> getAllProducts();

    Product createProduct(ProductDTO productDTO);

    Product updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);
}
