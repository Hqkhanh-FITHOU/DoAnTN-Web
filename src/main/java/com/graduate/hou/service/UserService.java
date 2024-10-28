package com.graduate.hou.service;

import com.graduate.hou.dto.request.UsersDTO;
import com.graduate.hou.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUser();
    User createUser(UsersDTO usersDTO);
    User updateUser(Long id, UsersDTO usersDTO);
    void deleteUser(Long id);

    Optional<User> findByUserName(String username);
}
