package com.graduate.hou.service;

import com.graduate.hou.dto.request.UsersDTO;
import com.graduate.hou.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUser();
    User createUser(UsersDTO usersDTO, MultipartFile avatarFile) throws IOException;
    boolean updateUser(Long id, UsersDTO usersDTO, MultipartFile avatarFile) throws IOException;
    boolean deleteUser(Long id);

    Optional<User> findByUserName(String username);
    Optional<User> findByPhone(String phone);

    User findByUserId(Long id);
}
