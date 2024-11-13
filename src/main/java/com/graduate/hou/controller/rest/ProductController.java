package com.graduate.hou.controller.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.graduate.hou.dto.request.ProductDTO;
import com.graduate.hou.entity.Product;
import com.graduate.hou.service.impl.ProductServiceImpl;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
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

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct (@PathVariable Long id){
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> updateProduct (@PathVariable Long id, @RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productService.updateProduct(id,productDTO));
    }

    @DeleteMapping("/delete/{id}")
    String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "Xóa thành công";
    }

}
