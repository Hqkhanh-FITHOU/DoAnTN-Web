package com.graduate.hou.service;

import com.graduate.hou.dto.UsersDTO;
import com.graduate.hou.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser();
    User createUser(UsersDTO usersDTO);
    User addUser(User user);
    User updateUser(Long id, UsersDTO usersDTO);
    void deleteUser(Long id);
}
