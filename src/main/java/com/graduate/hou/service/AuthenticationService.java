package com.graduate.hou.service;

import com.graduate.hou.dto.request.UserLoginDTO;
import com.graduate.hou.dto.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    TokenResponse login(UserLoginDTO loginDTO);

    TokenResponse refresh(HttpServletRequest request);

    String logout(HttpServletRequest request);
}
