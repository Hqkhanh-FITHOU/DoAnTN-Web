package com.graduate.hou.controller.rest;

import com.graduate.hou.dto.request.UserLoginDTO;
import com.graduate.hou.dto.request.UserRegisterDTO;
import com.graduate.hou.dto.request.UserRegisterDTO1;
import com.graduate.hou.dto.response.TokenResponse;
import com.graduate.hou.entity.User;
import com.graduate.hou.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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

    @PostMapping("/login")
    public ResponseEntity<?> login( @RequestBody UserLoginDTO loginDTO, HttpServletResponse response) throws Exception {
        log.info( "{username: "+loginDTO.getUsername()+", password: "+loginDTO.getPassword()+"}");
        TokenResponse tokenResponse = null;
        try {
            tokenResponse = authenticationService.login(loginDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        if (tokenResponse != null) {
            Cookie cookie = new Cookie("jwtToken", tokenResponse.getAccessToken());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.ok(tokenResponse);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public String refresh(){
        return "Success";
    }

    @PostMapping("/logout")
    public String logout(){
        return "Success";
    }

    @PostMapping("/register1")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO1 registerDTO, MultipartFile avatarFile) throws Exception {
        User userRegister = authenticationService.register1(registerDTO, avatarFile);
        return ResponseEntity.ok(userRegister);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO registerDTO) {
        User userRegister = authenticationService.register(registerDTO);
        return ResponseEntity.ok(userRegister);
    }


    
}
