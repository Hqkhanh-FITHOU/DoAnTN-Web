package com.graduate.hou.controller.rest;

import com.graduate.hou.dto.request.UsersDTO;
import com.graduate.hou.entity.User;
import com.graduate.hou.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Validated
@RestController
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private UserServiceImpl userService;


    @PostMapping("/new")
    public ResponseEntity<User> createNewUser(@RequestBody UsersDTO usersDTO){
        User savedUser = userService.createUser(usersDTO);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{phone}")
    public ResponseEntity<User> getUserByPhone(@PathVariable String phone){
        User user = userService.findByPhone(phone).get();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Long> getUserUserName(@PathVariable String username){
        User user = userService.findByUserName(username).get();
        return ResponseEntity.ok(user.getUserId());
    }

    @GetMapping("/")
    List<User> getAllUser(){
        return userService.getAllUser();
    }

    @PutMapping("/update/{id}")
    boolean updateUser (@PathVariable Long id, @RequestBody UsersDTO usersDTO){
        return userService.updateUser(id, usersDTO);
    }

    @DeleteMapping("/delete/{id}")
    String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "Xóa thành công";
    }

}
