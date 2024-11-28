package com.graduate.hou.controller;

import com.graduate.hou.dto.request.*;
import com.graduate.hou.entity.*;
import com.graduate.hou.mapper.*;
import com.graduate.hou.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduate.hou.enums.OrderStatus;
import com.graduate.hou.enums.PaymentMethod;
import com.graduate.hou.enums.PaymentStatus;
import com.graduate.hou.enums.RoleUsers;
import java.util.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/restaurant")
@Slf4j
public class RestaurantController {
    @Autowired
    private CouponService couponService;

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
        OrderDTO2 orderData = new OrderDTO2();
        orderData.setPaymentMethod(PaymentMethod.CASH_ON_DELIVERY);
        orderData.setPaymentStatus(PaymentStatus.PENDING);
        model.addAttribute("order", orderData);
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("coupons", couponService.getActiveCoupons());
        model.addAttribute("paymentMethods", PaymentMethod.values());
        model.addAttribute("paymentStatuses", PaymentStatus.values());
        return "orders/add";
    }

    @PostMapping("/orders/new")
    @ResponseBody
    public String saveOrder(@RequestBody OrderDTO2 orderDTO, Model model, Authentication authentication) {
        User user = userService.findByUserName(authentication.getName()).get();
        orderDTO.setUserId(user.getUserId());
        log.info("userId - "+user.getUserId());
        Order order = orderService.createOrder2(orderDTO);
        if(order == null){
            model.addAttribute("order", orderDTO);
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("coupons", couponService.getActiveCoupons());
            model.addAttribute("paymentMethods", PaymentMethod.values());
            model.addAttribute("paymentStatuses", PaymentStatus.values());
            return "orders/add";
        }
        return "{\"orderId\": "+order.getOrderId()+"}";
    }

    @PostMapping("/orderitem/save")
    @ResponseBody
    public String saveOrderItems(@RequestBody List<OrderItemDTO> orderItems) {
        boolean ok = true;
        if(orderItems.isEmpty()){
            ok = false;
        } else {
            for(OrderItemDTO dto : orderItems){
                if(orderItemService.createOrderItem(dto) == null){
                    ok = false;
                }
            }
        }
        return "{\"ok\": "+ok+"}";
    }


    @GetMapping("/orders/{id}/confirm")
    @ResponseBody
    public String confirmOrder(@PathVariable("id") Long id) {
        return "{ \"confirm\":"+ orderService.confirmOrder(id) +"}";
    }

    @GetMapping("/orders/{id}/updateDeliveryState")
    @ResponseBody
    public String updateDeliveryState(@PathVariable("id") Long id) {
        return "{ \"updated\":"+ orderService.updateState(id, OrderStatus.WILLING_DELIVERY) +"}";
    }
    
    @GetMapping("/orders/{id}/cancelOrder")
    @ResponseBody
    public String cancelOrder(@PathVariable("id") Long id) {
        return "{ \"canceled\":"+ orderService.updateState(id, OrderStatus.CANCELED) +"}";
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
        model.addAttribute("user", new UserRegisterDTO1());
        model.addAttribute("roles", RoleUsers.values());
        log.info("sdf",RoleUsers.values().toString());
        return "accounts/add";
    }

    @PostMapping("/accounts/new")
    public String saveAccount(@ModelAttribute("user") UserRegisterDTO1 usersDTO) throws Exception {
        User user = authenticationService.register1(usersDTO);
        if (user == null) {
            return "accounts/add";
        }
        return "redirect:/restaurant/accounts";
    }

    @GetMapping("/accounts/{id}/edit")
    public String goEditAccount(@PathVariable("id") Long id, Model model) throws IOException {
        User user = userService.findByUserId(id);

        model.addAttribute("roles", RoleUsers.values());
        model.addAttribute("accounts", UserMapper.toDTO(user));
        return "accounts/edit";
    }

    @PostMapping("/accounts/{id}/edit")
    public String updateAccount(@PathVariable Long id,
                                @ModelAttribute("user") UsersDTO usersDTO,
                                Model model) throws IOException{
        if(userService.updateUser(id, usersDTO)){
            return "redirect:/restaurant/accounts";
        }
        model.addAttribute("roles", RoleUsers.values());
        model.addAttribute("accounts", usersDTO);
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

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestParam("avatar") MultipartFile avatar,
            @RequestParam("fullname") String fullname,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("password") String password,
            @RequestParam("roles") Set<RoleUsers> roles) {
        try {
            // Tạo DTO từ các tham số
            UserRegisterDTO1 registerDTO = new UserRegisterDTO1();
            registerDTO.setFullname(fullname);
            registerDTO.setUsername(username);
            registerDTO.setEmail(email);
            registerDTO.setPhone(phone);
            registerDTO.setPassword(password);
            registerDTO.setRoles(roles);
            registerDTO.setAvatar(avatar);
            
            // Gọi service để lưu người dùng
            User user = authenticationService.register1(registerDTO);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/coupons")
    public String goCouponsManagement(Model model) {
        List<Coupon> coupons = this.couponService.getAllCoupon();
        model.addAttribute("coupons", coupons);
        return "coupons";
    }

    @GetMapping("/coupons/{id}")
    @ResponseBody
    public String getMethodName(@PathVariable("id") Long id) {
        Coupon coupon = couponService.getCouponById(id);
        try {
            return objectMapper.writeValueAsString(coupon);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "{\"error\": \"Could not serialize coupon id "+id+"\"}"; 
        }
    }
    

    @GetMapping("/coupons/new")
    public String goAddNewCoupon(Model model) {
        model.addAttribute("coupons", new CouponDTO());

        return "coupons/add";
    }

    @PostMapping("/coupons/new")
    public String saveCoupon(@ModelAttribute CouponDTO couponDTO) {
        Coupon coupon = couponService.createCoupon(couponDTO);
        if(coupon == null){
            return "coupons/add";
        }
        return "redirect:/restaurant/coupons";
    }

    @GetMapping("/coupons/{id}/edit")
    public String goEditCoupon(@PathVariable("id") Long id, Model model) {
        Coupon coupon = couponService.getCouponById(id);
        model.addAttribute("coupons", CouponMapper.toDTO(coupon));
        return "coupons/edit";
    }

    @PostMapping("/coupons/{id}/edit")
    public String updateCOrder(@PathVariable("id") Long id, @ModelAttribute CouponDTO couponDTO) {
        if(couponService.updateCoupon(id, couponDTO)){
            return "redirect:/restaurant/coupons";
        }
        return "coupons/edit";
    }

    @GetMapping("/coupons/{id}/delete")
    @ResponseBody
    public String deleteCoupon(@PathVariable("id") Long id) {
        return "{ \"delete\":"+ couponService.deleteCoupon(id) +"}";
    }

    @PostMapping("/coupons/{id}/disable")
    public String disableCoupon(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            couponService.disableCoupon(id);
            redirectAttributes.addFlashAttribute("message", "Khuyến mãi này đã được vô hiệu hóa thành công.");
            return "redirect:/restaurant/coupons"; // Quay lại danh sách coupon
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi vô hiệu hóa khuyến mãi.");
            return "redirect:/restaurant/coupons"; // Quay lại danh sách coupon
        }
    }

    @PostMapping("/coupons/{id}/enable")
    public String enableCoupon(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            couponService.enableCoupon(id);
            redirectAttributes.addFlashAttribute("message", "Loại khuyến mãi đã được kích hoạt lại thành công.");
            return "redirect:/restaurant/coupons"; // Quay lại danh sách coupon
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi kích hoạt lại khuyến mãi, vì khuyến mãi hết hạn.");
            return "redirect:/restaurant/coupons"; // Quay lại danh sách coupon
        }
    }
}
