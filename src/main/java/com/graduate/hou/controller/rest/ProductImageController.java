package com.graduate.hou.controller.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.graduate.hou.dto.request.ProductImageDTO;
import com.graduate.hou.entity.ProductImage;
import com.graduate.hou.service.impl.ProductImageServiceImpl;

@RestController
@RequestMapping("/productimage")
public class ProductImageController {
    @Autowired
    private ProductImageServiceImpl productImageService;

    @PostMapping("/new")
    public ResponseEntity<ProductImage> createNewResponse(@RequestBody ProductImageDTO productImageDTO) {
        ProductImage productImage = productImageService.saveProductImage(productImageDTO);
        return ResponseEntity.ok(productImage);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<ProductImage>> getAllResponse(){
        List<ProductImage> responses = productImageService.getAllProductImages();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductImage> updateProductImage (@PathVariable Long id, @RequestBody ProductImageDTO productImageDTO){
        ProductImage productImage = productImageService.updateProductImage(id,productImageDTO);
        return ResponseEntity.ok(productImage);
    }

    @DeleteMapping("/{id}")
    String deleteProductImage(@PathVariable Long id){
        productImageService.deleteProductImage(id);
        return "Xóa thành công";
    }
}
