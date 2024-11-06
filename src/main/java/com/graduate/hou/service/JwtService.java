package com.graduate.hou.service;

import com.graduate.hou.enums.TokenType;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    String extractUsername(String token, TokenType type);
    boolean isValidate(String token,TokenType type, UserDetails user);
}
