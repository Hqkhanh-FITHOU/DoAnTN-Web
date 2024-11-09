package com.graduate.hou.service.impl;

import com.graduate.hou.entity.CustomUserDetails;
import com.graduate.hou.enums.TokenType;
import com.graduate.hou.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.expiryTime}")
    private long exprityTime;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.refreshKey}")
    private String refreshKey;


    @Override
    public String generateToken(CustomUserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return generateRefreshToken(new HashMap<>(), userDetails);
    }

    @Override
    public String extractUsername(String token, TokenType type) {
        return extractClaim(token, type, Claims::getSubject);
    }

    @Override
    public boolean isValidate(String token, TokenType type, UserDetails userDetails) {
        final String username = extractUsername(token, type);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token, type);
    }

    private String generateToken(HashMap<String, Object> claims,CustomUserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + exprityTime))
                .signWith(getKey(TokenType.ACCESS_TOKEN),SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateRefreshToken(HashMap<String, Object> claims,UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (exprityTime*24*14)))
                .signWith(getKey(TokenType.REFRESH_TOKEN),SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(TokenType type) {
        byte[] keyBytes;
        if (TokenType.ACCESS_TOKEN.equals(type)) {
            keyBytes = Decoders.BASE64.decode(secretKey);
        }else {
            keyBytes = Decoders.BASE64.decode(refreshKey);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private <T> T extractClaim(String token, TokenType type, Function<Claims, T> claimsTFunction){
        final  Claims claims = extractAllClaim(token, type);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaim(String token, TokenType type) {
        return Jwts.parserBuilder().setSigningKey(getKey(type)).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token,TokenType type) {
        return extractExpiration(token,type).before(new Date());
    }

    private Date extractExpiration(String token,TokenType type) {
        return extractClaim(token,type, Claims::getExpiration);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean validateToken(String token) {
        try {
            // Parse và kiểm tra chữ ký của token
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            // Kiểm tra nếu token đã hết hạn
            return !claims.getExpiration().before(new Date());
            
        } catch (ExpiredJwtException e) {
            System.out.println("JWT đã hết hạn: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT không được hỗ trợ: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("JWT không hợp lệ: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("Chữ ký JWT không hợp lệ: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Chuỗi JWT trống hoặc có lỗi: " + e.getMessage());
        }
        return false;
    }


}
