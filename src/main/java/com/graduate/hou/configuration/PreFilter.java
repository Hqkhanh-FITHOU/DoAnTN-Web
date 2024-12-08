package com.graduate.hou.configuration;

import com.graduate.hou.enums.TokenType;
import com.graduate.hou.service.JwtService;
import com.graduate.hou.service.impl.CustomUserDetailsService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class PreFilter extends OncePerRequestFilter {
    @Autowired
    private final CustomUserDetailsService customUserDetailsService;
    @Autowired
    private final JwtService jwtService;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain
    )   throws ServletException, IOException {

        final String authorization = request.getHeader("Authorization");
        //log.info("Authorization: {}", authorization);
        
        if (StringUtils.isBlank(authorization) || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            //log.info("Authorization is blank!");
            return;
        }

        try {
            final String token = authorization.substring("Bearer".length());

            final String userName = jwtService.extractUsername(token, TokenType.ACCESS_TOKEN);

            if (StringUtils.isNotEmpty(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

                if (jwtService.isValidate(token, TokenType.ACCESS_TOKEN, userDetails)) {
                    //log.info("PreFilter: validate token {true}");
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authenticationToken);
                    SecurityContextHolder.setContext(context);

                }
            }
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed"); // Gửi lỗi xác thực nếu có lỗi
            return;
        }
        filterChain.doFilter(request, response);
    }
}
