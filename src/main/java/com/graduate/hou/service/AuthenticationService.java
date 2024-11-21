package com.graduate.hou.service;

import com.graduate.hou.dto.request.UserLoginDTO;
import com.graduate.hou.dto.request.UserRegisterDTO;
import com.graduate.hou.dto.request.UserRegisterDTO1;
import com.graduate.hou.dto.response.TokenResponse;
import com.graduate.hou.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AuthenticationService {
    TokenResponse login(UserLoginDTO loginDTO);

    TokenResponse refresh(HttpServletRequest request);

    String logout(HttpServletRequest request);

    User register1(UserRegisterDTO1 registerDTO, MultipartFile avatarFile) throws  Exception;

    User register(UserRegisterDTO registerDTO);
}
