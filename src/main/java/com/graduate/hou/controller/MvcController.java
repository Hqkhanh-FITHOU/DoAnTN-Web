package com.graduate.hou.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/restaurant")
@Slf4j
public class MvcController {

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
    public String goOrdersManagement(){
        return "orders";
    }
    
    @GetMapping("/products")
    public String goProductsManagement() {
        return "products";
    }

    @GetMapping("/accounts")
    public String goAccountsManagement() {
        return "accounts";
    }
    
    
}
