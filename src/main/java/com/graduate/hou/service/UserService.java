package com.graduate.hou.service;

import com.graduate.hou.dto.request.UsersDTO;
import com.graduate.hou.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    List<User> getAllUser();
    User createUser(UsersDTO usersDTO);
    User updateUser(Long id, UsersDTO usersDTO);
    void deleteUser(Long id);
}
