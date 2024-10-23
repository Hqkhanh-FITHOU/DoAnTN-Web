package com.graduate.hou.service.impl;

import com.graduate.hou.dto.request.UserLoginDTO;
import com.graduate.hou.dto.response.TokenResponse;
import com.graduate.hou.repository.UsersRepository;
import com.graduate.hou.service.AuthenticationService;
import com.graduate.hou.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UsersRepository usersRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Override
    public TokenResponse login(UserLoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        }catch (Exception e){
            System.out.println("Authentication failed" + e.getMessage());
        }

        var user = usersRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(()-> new RuntimeException("tên người dùng không tồn tại"));

        String accessToken = jwtService.generateToken(user);
        String resfeshToken = jwtService.generateToken(user);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .resfreshToken(resfeshToken)
                .userId(user.getUserId())
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
}
