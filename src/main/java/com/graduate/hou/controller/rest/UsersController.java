package com.graduate.hou.controller.rest;


public class UsersController {


import com.graduate.hou.dto.UsersDTO;
import com.graduate.hou.entity.User;
import com.graduate.hou.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    User createUser(@RequestBody UsersDTO usersDTO){
        return userService.createUser(usersDTO);
    }

    @GetMapping
    List<User> getAllUser(){
        return userService.getAllUser();
    }

    @PutMapping("/{id}")
    User updateUser (@PathVariable Long id, @RequestBody UsersDTO usersDTO){
        return userService.updateUser(id, usersDTO);
    }

    @DeleteMapping("/{id}")
    String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "Xóa thành công";
    }

}
