package com.graduate.hou.configuration;


import com.graduate.hou.service.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
                registry.addMapping("**")
                        .allowedOrigins("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(false)
                        .maxAge(36000);
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf
                .disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(WHILELIST).permitAll()
                        .requestMatchers("/*").permitAll()
                        .requestMatchers("/restaurant/**").hasAuthority("ADMIN")
                        .requestMatchers("/restaurant/login/**").permitAll()
                        .requestMatchers("/user/**").hasAuthority("ADMIN")
                        .requestMatchers("/auth/**").permitAll()
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
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
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
