package com.graduate.hou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduate.hou.dto.request.CategoryDTO;
import com.graduate.hou.dto.request.ProductDTO;
import com.graduate.hou.dto.request.ProductImageDTO;
import com.graduate.hou.dto.request.UserRegisterDTO;
import com.graduate.hou.entity.Category;
import com.graduate.hou.entity.Product;
import com.graduate.hou.entity.ProductImage;
import com.graduate.hou.enums.RoleUsers;
import com.graduate.hou.mapper.CategoryMapper;
import com.graduate.hou.mapper.ProductMapper;
import com.graduate.hou.service.CategoryService;
import com.graduate.hou.service.ProductImageService;
import com.graduate.hou.service.ProductService;
import com.graduate.hou.service.StorageService;
import java.util.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;





@Controller
@RequestMapping("/restaurant")
@Slf4j
public class RestaurantController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/dashboard")
    public String goDashBoard() {
        return "index";
    }

    @GetMapping("/login")
    public String goLogin(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/restaurant/dashboard";
        }
        return "authenticate";
    }

    @GetMapping("/orders")
    public String goOrdersManagement() {
        return "orders";
    }

    @GetMapping("/categories")
    public String goCategoriesManagement(Model model) {
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/categories/new")
    public String goAddNewCategory(Model model) {
        model.addAttribute("category", new CategoryDTO());
        return "categories/add";
    }

    @PostMapping("/categories/new")
    public String saveCategory(@ModelAttribute CategoryDTO categoryDTO) {
        Category category = categoryService.createCategory(categoryDTO);
        if(category == null){
            return "categories/add";
        }
        return "redirect:/restaurant/categories";
    }

    @GetMapping("/categories/{id}/edit")
    public String goEditCategory(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.getCategory(id);
        model.addAttribute("category", CategoryMapper.toDTO(category));
        return "categories/edit";
    }
    
    @PostMapping("/categories/{id}/edit")
    public String updateCategory(@PathVariable("id") Long id, @ModelAttribute CategoryDTO categoryDTO) {
        if(categoryService.updateCategory(id, categoryDTO)){
            return "redirect:/restaurant/categories";
        }
        return "categories/edit";
    }
    
    

    @GetMapping("/categories/{id}/delete")
    @ResponseBody
    public String deleteCategory(@PathVariable("id") Long id) {
        return "{ \"delete\":"+ categoryService.deleteCategory(id) +"}";
    }
    

    @GetMapping("/products")
    public String goProductsManagement(Model model) {
        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/accounts")
    public String goAccountsManagement() {
        return "accounts";
    }

    @GetMapping("/products/new")
    public String goAddNewProduct(Model model) {
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "products/add";
    }

    @PostMapping("/products/new")
    public String saveProduct(@ModelAttribute("product") ProductDTO productDTO) {
        Product newProduct = productService.createProduct(productDTO); // save to database
        if (newProduct == null) {
            return "products/add";
        }
        int count = 0;
        for (MultipartFile file : productDTO.getImages()) {
            if (!file.isEmpty()) {
                count++;
                String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS"));
                String fileName = newProduct.getProductId() + "_" + count + "_" + timeStamp + "_"
                        + file.getOriginalFilename();
                storageService.storeProductImage(file, fileName);
                ProductImageDTO imageDTO = new ProductImageDTO();
                imageDTO.setProductId(newProduct.getProductId());
                imageDTO.setPathString(fileName);
                this.productImageService.saveProductImage(imageDTO);
            }
        }
        return "redirect:/restaurant/products";
    }

    @GetMapping("/products/{id}/imgs")
    @ResponseBody
    public String getProductImages(@PathVariable("id") Long id) {
        Product product = productService.findProductById(id);
        if (product == null) {
            return "";
        }
        try {
            return objectMapper.writeValueAsString(product.getProductImages());
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Could not serialize products\"}";
        }
    }

    @GetMapping("/products/{id}/edit")
    public String updateProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("product", ProductMapper.toDTO(productService.findProductById(id)));
        return "products/edit";
    }


    @PostMapping("/products/{id}/edit")
    public String postMethodName(@PathVariable("id") Long id, @ModelAttribute("product") ProductDTO productDTO) {
        if (!productService.updateProduct(id,productDTO)) {
            return "products/edit";
        }
        int count = 0;
        for (MultipartFile file : productDTO.getImages()) {
            if (!file.isEmpty()) {
                count++;
                String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS"));
                String fileName = id + "_" + count + "_" + timeStamp + "_"
                        + file.getOriginalFilename();
                storageService.storeProductImage(file, fileName);
                ProductImageDTO imageDTO = new ProductImageDTO();
                imageDTO.setProductId(id);
                imageDTO.setPathString(fileName);
                this.productImageService.saveProductImage(imageDTO);
            }
        }
        return "redirect:/restaurant/products";
    }
    


    @PostMapping("/products/{id}/delete-image")
    @ResponseBody
    public String deleteUnusedProductImgs(@PathVariable("id") Long id, @RequestBody List<Long> imageIdList) {
        for(Long imgId : imageIdList){
            ProductImage image = productImageService.finProductImageById(imgId);
            storageService.removeProductImage(image.getPathString());
            productImageService.deleteProductImage(imgId);
        }
        return "oke";
    }
    

    @GetMapping("/products/{id}/delete")
    @ResponseBody
    public String deleteProduct(@PathVariable("id") Long id) {
        Product product = productService.findProductById(id);
        for(ProductImage img : product.getProductImages()){
            storageService.removeProductImage(img.getPathString());
            productImageService.deleteProductImage(img.getImageId());
        }
        return "{ \"delete\":"+ productService.deleteProduct(id) +"}";
    }


    @GetMapping("/accounts/new")
    public String goAddNewUser(Model model) {
        model.addAttribute("user", new UserRegisterDTO());
        model.addAttribute("roles", RoleUsers.values());
        log.info("sdf",RoleUsers.values().toString());
        return "accounts/add";
    }
    
}
