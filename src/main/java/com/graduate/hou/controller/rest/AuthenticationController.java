package com.graduate.hou.controller.rest;

import com.graduate.hou.dto.request.UserLoginDTO;
import com.graduate.hou.service.AuthenticationService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping
    public String hello(){
        return "xin chao";
    }

    @PostMapping("/access")
    public ResponseEntity<?> login( @RequestBody UserLoginDTO loginDTO) throws Exception {
        return new ResponseEntity<>(authenticationService.login(loginDTO), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public String refresh(){
        return "Success";
    }

    @PostMapping("/logout")
    public String logout(){
        return "Success";
    }
}
