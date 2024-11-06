package com.graduate.hou.service.impl;

import com.graduate.hou.dto.request.UserLoginDTO;
import com.graduate.hou.dto.request.UserRegisterDTO;
import com.graduate.hou.dto.response.TokenResponse;
import com.graduate.hou.entity.CustomUserDetails;
import com.graduate.hou.entity.User;
import com.graduate.hou.repository.UsersRepository;
import com.graduate.hou.service.AuthenticationService;
import com.graduate.hou.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UsersRepository usersRepository;
    
    private final CustomUserDetailsService userDetailsService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;


    @Override
    public TokenResponse login(UserLoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        }catch (Exception e){
            System.out.println("Authentication failed" + e.getMessage());
        }
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(loginDTO.getUsername());
        String accessToken = jwtService.generateToken(userDetails);
        return TokenResponse.builder()
            .accessToken(accessToken)
            .username(userDetails.getUsername())
            .build();
    }


    @Override
    public TokenResponse refresh(HttpServletRequest request) {
        return null;
    }


    @Override
    public String logout(HttpServletRequest request) {
        return null;
    }


    @Override
    public User register(UserRegisterDTO registerDTO) {
        User user = new User();
        user.setFullname(registerDTO.getFullname());
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPhone(registerDTO.getPhone());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRoles(registerDTO.getRoles());
        user.setUserPoint(0L);
        return usersRepository.save(user);
    }
}
