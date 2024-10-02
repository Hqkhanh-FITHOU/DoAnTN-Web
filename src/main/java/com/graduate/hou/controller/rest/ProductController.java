package com.graduate.hou.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.graduate.hou.dto.ProductDTO;
import com.graduate.hou.entity.Product;
import com.graduate.hou.service.impl.ProductServiceImpl;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;

    @PostMapping("/new")
    public ResponseEntity<Product> createNewProduct(@RequestBody ProductDTO productDTO){
        Product product = productService.createProduct(productDTO);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProduct(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct (@PathVariable Long id, @RequestBody ProductDTO productDTO){
        Product product = productService.updateProduct(id,productDTO);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "Xóa thành công";
    }

}
