package com.graduate.hou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;





@Controller
@RequestMapping("/restaurant")
public class MvcController {

    @GetMapping("/dashboard")
    public String goDashBoard() {
        return "index";
    }
    

    @GetMapping("/authenticate")
    public String goLogin() {
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
