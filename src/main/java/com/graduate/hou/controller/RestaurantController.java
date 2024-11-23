package com.graduate.hou.controller;

import com.graduate.hou.dto.request.*;
import com.graduate.hou.entity.*;
import com.graduate.hou.mapper.OrderMapper;
import com.graduate.hou.mapper.UserMapper;
import com.graduate.hou.service.*;
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
import com.graduate.hou.enums.RoleUsers;
import com.graduate.hou.mapper.CategoryMapper;
import com.graduate.hou.mapper.ProductMapper;

import java.util.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;







@Controller
@RequestMapping("/restaurant")
@Slf4j
public class RestaurantController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AddressService addressService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private StorageService storageService;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/dashboard")
    public String goDashBoard() {
        return "index";
    }


    @GetMapping("/login")
    public String goLogin(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/restaurant/dashboard";
        }
        return "authenticate";
    }

    @GetMapping("/orders")
    public String goOrdersManagement(Model model) {
        List<Order> orders = this.orderService.getAllOrder();
        List<Order> pendingOrders = this.orderService.getPendingOrder();
        model.addAttribute("orders", orders);
        model.addAttribute("pendingOrders", pendingOrders);
        return "orders";
    }

    @GetMapping("/orders/new")
    public String goAddNewOrder(Model model) {
        model.addAttribute("order", new OrderDTO2());
        model.addAttribute("products", productService.getAllProducts());
        return "orders/add";
    }

    @PostMapping("/orders/new")
    public String saveOrder(@ModelAttribute OrderDTO orderDTO) {
        Order order = orderService.createOrder(orderDTO);
        if(order == null){
            return "orders/add";
        }
        return "redirect:/restaurant/orders";
    }

    @GetMapping("/orders/{id}/confirm")
    @ResponseBody
    public String confirmOrder(@PathVariable("id") Long id) {
        return "{ \"confirm\":"+ orderService.confirmOrder(id) +"}";
    }
    

    @GetMapping("/orders/{id}/edit")
    public String goEditOrder(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getAllUser());
        model.addAttribute("payment", paymentService.getAllPayment());
        model.addAttribute("address", addressService.getAllAddress());
        model.addAttribute("order", OrderMapper.toDTO(orderService.findOrderById(id)));
        return "orders/edit";
    }

    @PostMapping("/orders/{id}/edit")
    public String updateCOrder(@PathVariable("id") Long id, @ModelAttribute OrderDTO orderDTO) {
        if(orderService.updateOrder(id, orderDTO)){
            return "redirect:/restaurant/orders";
        }
        return "orders/edit";
    }

    @GetMapping("/orders/{id}/delete")
    @ResponseBody
    public String deleteOrder(@PathVariable("id") Long id) {
        return "{ \"delete\":"+ orderService.deleteOrder(id) +"}";
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



    @GetMapping("/products/new")
    public String goAddNewProduct(Model model) {
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "products/add";
    }

    @PostMapping("/products/new")
    public String saveProduct(@ModelAttribute("product") ProductDTO productDTO, Model model) {
        if(productService.isExistProductWithName(productDTO.getName(), 0L)) {
            model.addAttribute("errorName", "Đã tồn tại món ăn có tên này");
            model.addAttribute("categories", categoryService.getAllCategory());
            model.addAttribute("product", productDTO);
            return "products/add";
        }
        Product newProduct = productService.createProduct(productDTO); // save to database
        if (newProduct == null) {
            model.addAttribute("product", productDTO);
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
        model.addAttribute("productId", id);
        return "products/edit";
    }


    @PostMapping("/products/{id}/edit")
    public String postMethodName(@PathVariable("id") Long id, @ModelAttribute("product") ProductDTO productDTO, Model model) {
        if(productService.isExistProductWithName(productDTO.getName(), productDTO.getProductId())) {
            model.addAttribute("errorName", "Đã tồn tại món ăn có tên này");
            model.addAttribute("categories", categoryService.getAllCategory());
            model.addAttribute("product", productDTO);
            return "products/edit";
        }
        if (!productService.updateProduct(id,productDTO)) {
            model.addAttribute("product", productDTO);
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
        boolean deletable = !productService.existInAnyOrder(id);
        if(deletable){
            for(ProductImage img : product.getProductImages()){
                storageService.removeProductImage(img.getPathString());
                productImageService.deleteProductImage(img.getImageId());
            }
        }
        return "{ \"delete\":"+ productService.deleteProduct(id) +"}";
    }

    @GetMapping("/accounts")
    public String goAccountsManagement(Model model, Authentication authentication) {
        List<User> users = this.userService.getAllUser();
        User user = userService.findByUserName(authentication.getName()).get();
        model.addAttribute("accounts" , users);
        model.addAttribute("currentAccountId", user.getUserId());
        return "accounts";
    }

    @GetMapping("/accounts/new")
    public String goAddNewUser(Model model) {
        model.addAttribute("user", new UserRegisterDTO());
        model.addAttribute("roles", RoleUsers.values());
        log.info("sdf",RoleUsers.values().toString());
        return "accounts/add";
    }

    @PostMapping("/accounts/new")
    public String saveAccount(@ModelAttribute("account") UserRegisterDTO usersDTO) {
        User user = authenticationService.register(usersDTO); // save to database
        if (user == null) {
            return "accounts/add";
        }
        return "redirect:/restaurant/accounts";
    }

    @GetMapping("/accounts/{id}/edit")
    public String goEditAccount(@PathVariable("id") Long id, Model model) {
        User  user = userService.findByUserId(id);
        model.addAttribute("roles", RoleUsers.values());
        model.addAttribute("accounts", UserMapper.toDTO(user));
        return "accounts/edit";
    }

    @PostMapping("/accounts/{id}/edit")
    public String updateAccount(@PathVariable("id") Long id, @ModelAttribute UsersDTO usersDTO) {
        if(userService.updateUser(id, usersDTO)){
            return "redirect:/restaurant/accounts";
        }
        return "accounts/edit";
    }

    @GetMapping("/accounts/{id}/delete")
    @ResponseBody
    public String deleteAccount(@PathVariable("id") Long id) {
        return "{ \"delete\":"+ userService.deleteUser(id) +"}";
    }


    @GetMapping("/orderitems/{orderId}")
    @ResponseBody
    public String getOrderItemsByOrderId(@PathVariable("orderId") Long orderId) {
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(orderId);
        log.info(orderItems.get(0).getOrderItemId()+"");
        try {
            return objectMapper.writeValueAsString(orderItems);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Could not serialize products\"}";
        }
    }
    

}
