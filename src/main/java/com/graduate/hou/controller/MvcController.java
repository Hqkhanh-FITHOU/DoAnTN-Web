package com.graduate.hou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/restaurant")
public class MvcController {

    @GetMapping("/dashboard")
    public String getMethodName() {
        return "index";
    }
    


}
