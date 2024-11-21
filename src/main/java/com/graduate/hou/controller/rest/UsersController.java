package com.graduate.hou.controller.rest;

import com.graduate.hou.dto.request.UsersDTO;
import com.graduate.hou.entity.User;
import com.graduate.hou.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private UserServiceImpl userService;


    @PostMapping("/new")
    public ResponseEntity<User> createNewUser(@RequestBody UsersDTO usersDTO, MultipartFile avatarFile) throws IOException {
        User savedUser = userService.createUser(usersDTO, avatarFile);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/")
    List<User> getAllUser(){
        return userService.getAllUser();
    }

    @PutMapping("/update/{id}")
    boolean updateUser (@PathVariable Long id, @RequestBody UsersDTO usersDTO, MultipartFile avatarFile)throws IOException{
        return userService.updateUser(id, usersDTO, avatarFile);
    }

    @DeleteMapping("/delete/{id}")
    String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "Xóa thành công";
    }

}
