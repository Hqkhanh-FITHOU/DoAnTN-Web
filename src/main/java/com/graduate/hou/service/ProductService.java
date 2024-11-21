package com.graduate.hou.service;

import java.util.List;
import com.graduate.hou.dto.request.ProductDTO;
import com.graduate.hou.entity.Product;

public interface ProductService {
    
    List<Product> getAllProducts();

    Product findProductById(Long id);

    boolean isExistProductWithName(String name, Long id);

    Product createProduct(ProductDTO productDTO);

    boolean updateProduct(Long id, ProductDTO productDTO);

    boolean deleteProduct(Long id);

    boolean existInAnyOrder(Long id);
}
