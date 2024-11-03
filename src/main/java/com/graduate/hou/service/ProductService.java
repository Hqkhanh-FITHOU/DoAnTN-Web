package com.graduate.hou.service;

import java.util.List;
import com.graduate.hou.dto.request.ProductDTO;
import com.graduate.hou.entity.Product;

public interface ProductService {
    
    List<Product> getAllProducts();

    Product findProductById(Long id);

    Product createProduct(ProductDTO productDTO);

    boolean updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);
}
