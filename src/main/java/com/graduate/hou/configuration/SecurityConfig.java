package com.graduate.hou.configuration;


import com.graduate.hou.service.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final PreFilter preFilter;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    private String[] WHILELIST = { 
        "/css/**",
        "/img/**",
        "/js/**",
        "/fonts/**",
        "/ws/**",
    };

    @Bean
    public WebMvcConfigurer cosConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .allowCredentials(false)
                        .maxAge(36000);
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:*", "http://192.168.0.106:*", "*")); // Thay bằng URL của ứng dụng Flutter
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @SuppressWarnings("unused")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(WHILELIST).permitAll()
                        .requestMatchers(
                            "/restaurant/**"
                            ).hasAuthority("ADMIN")
                        .requestMatchers("/restaurant/login/**").permitAll()
                        .requestMatchers(
                            "/auth/**",
                            "/product/**",
                            "/category/**",
                            "/productimage/**",
                            "/uploads/**",
                            "/orderItem/**",
                            "/order/**",
                            "/notification/**",
                            "/user/**",
                            "/response/**",
                            "/category/**",
                            "/review/**",
                            "/coupon/**"
                            ).permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/restaurant/login")
                        .loginProcessingUrl("/restaurant/login")
                        .usernameParameter("username").passwordParameter("password")
                        .defaultSuccessUrl("/restaurant/dashboard")
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/L1/restaurant/dashboard");
                        })
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/restaurant/logout") // URL xử lý logout
                        .logoutSuccessUrl("/restaurant/login?logout") // Trang chuyển hướng sau khi logout
                        .invalidateHttpSession(true) // Vô hiệu hóa session
                        .permitAll())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1) //mỗi người dùng chỉ có 1 phiên đăng nhập
                        .expiredSessionStrategy(new SimpleRedirectSessionInformationExpiredStrategy("/restaurant/login?logout")))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(preFilter, UsernamePasswordAuthenticationFilter.class);
        log.debug("security-config.securityFilterChain: go authenticate.html");
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
