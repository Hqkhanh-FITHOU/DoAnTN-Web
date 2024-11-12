package com.graduate.hou.service;

import com.graduate.hou.dto.request.UsersDTO;
import com.graduate.hou.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUser();
    User createUser(UsersDTO usersDTO);
    boolean updateUser(Long id, UsersDTO usersDTO);
    boolean deleteUser(Long id);

    Optional<User> findByUserName(String username);

    User findByUserId(Long id);
}
