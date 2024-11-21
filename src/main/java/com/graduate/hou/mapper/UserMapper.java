package com.graduate.hou.mapper;

import com.graduate.hou.dto.request.UsersDTO;
import com.graduate.hou.entity.User;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.IOException;

public class UserMapper {

    public static UsersDTO toDTO(User user) {
        UsersDTO userDTO = new UsersDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhone(user.getPhone());
        userDTO.setFullname(user.getFullname());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }
}
