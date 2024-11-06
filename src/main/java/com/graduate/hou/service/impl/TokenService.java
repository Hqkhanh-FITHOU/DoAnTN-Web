package com.graduate.hou.service.impl;

import com.graduate.hou.entity.Token;
import com.graduate.hou.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public record TokenService(TokenRepository tokenRepository) {
    public long save(Token token) {
        Optional<Token> optional = tokenRepository.findByUsername(token.getUsername());
        if (optional.isEmpty()) {
            tokenRepository.save(token);
            return token.getId();
        }else {
            Token currentToken = optional.get();
            currentToken.setAccessToken(token.getAccessToken());
            currentToken.setRefressToken(token.getRefressToken());
            tokenRepository.save(currentToken);
            return currentToken.getId();
        }
    }
    public String delete(Token token) {
        tokenRepository.delete(token);
        return "Deleted!";
    }
    public Token getByUserName(String email) {
        return tokenRepository.findByUsername(email).orElseThrow(()-> new RuntimeException("Không tồn tại"));
    }
}
