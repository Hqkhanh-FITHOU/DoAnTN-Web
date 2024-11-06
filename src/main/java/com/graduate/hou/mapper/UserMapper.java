package com.graduate.hou.mapper;

import com.graduate.hou.dto.request.UsersDTO;
import com.graduate.hou.entity.User;

public class UserMapper {

    public static UsersDTO toDTO(User user){
        UsersDTO userDTO = new UsersDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhone(user.getPhone());
        userDTO.setFullname(user.getFullname());
        return userDTO;
    }

}
