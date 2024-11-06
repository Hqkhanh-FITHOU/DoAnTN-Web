package com.graduate.hou.service.impl;

import com.graduate.hou.dto.request.UserLoginDTO;
import com.graduate.hou.dto.response.TokenResponse;
import com.graduate.hou.entity.Token;
import com.graduate.hou.entity.User;
import com.graduate.hou.enums.TokenType;
import com.graduate.hou.repository.UsersRepository;
import com.graduate.hou.service.AuthenticationService;
import com.graduate.hou.service.JwtService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UsersRepository usersRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenService tokenService;
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
        String resfeshToken = jwtService.generateRefreshToken(user);

        tokenService.save(Token.builder().username(user.getUsername()).accessToken(accessToken).refressToken(resfeshToken).build());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(resfeshToken)
                .userId(user.getUserId())
                .build();
    }

    @Override
    public TokenResponse refresh(HttpServletRequest request) {
        String refreshToken=request.getHeader("x-token");
        if (StringUtils.isBlank(refreshToken)) {
            throw new RuntimeException("Token must be not blank");
        }
        //extract user from token
        final String username=jwtService.extractUsername(refreshToken, TokenType.REFRESH_TOKEN);
        //check it into db
        Optional<User> user = usersRepository.findByUsername(username);

        if (!jwtService.isValidate(refreshToken, TokenType.REFRESH_TOKEN,user.get())) {
            throw new RuntimeException("Token is invalid");
        }
        String accessToken=jwtService.generateToken(user.get());
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.get().getUserId())
                .build();
    }

    @Override
    public String logout(HttpServletRequest request) {
        String refreshToken=request.getHeader("x-token");
        if (StringUtils.isBlank(refreshToken)) {
            throw new RuntimeException("ErrorCode.TOKEN_IS_NOT_BLANK");
        }
        //extract user from token
        final String username = jwtService.extractUsername(refreshToken, TokenType.ACCESS_TOKEN);
        Token currentToken=tokenService.getByUserName(username);
        tokenService.delete(currentToken);
        return "Deleted!";
    }
}
