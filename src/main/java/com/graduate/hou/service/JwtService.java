package com.graduate.hou.service;

import com.graduate.hou.entity.CustomUserDetails;
import com.graduate.hou.enums.TokenType;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(CustomUserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    String extractUsername(String token);
    boolean isValidate(String token,TokenType type, UserDetails user);
    boolean validateToken(String token);
}
