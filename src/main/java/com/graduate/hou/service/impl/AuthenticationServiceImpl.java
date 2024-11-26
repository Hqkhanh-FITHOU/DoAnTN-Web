package com.graduate.hou.service.impl;

import com.graduate.hou.dto.request.UserLoginDTO;
import com.graduate.hou.dto.request.UserRegisterDTO;
import com.graduate.hou.dto.request.UserRegisterDTO1;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        } catch (UsernameNotFoundException ex) {
            System.out.println("Authentication failed = User not found! >" + ex.getMessage());
            throw new UsernameNotFoundException("User not found!");
        } catch (BadCredentialsException e){
            System.out.println("Authentication failed = Wrong username or password! >" + e.getMessage());
            throw new BadCredentialsException("Wrong username or password!");
        } catch (Exception e){
            System.out.println("Authentication failed " + e.getMessage());
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
    public User register1(UserRegisterDTO1 registerDTO) throws Exception {


        User user = new User();
        user.setFullname(registerDTO.getFullname());
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPhone(registerDTO.getPhone());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRoles(registerDTO.getRoles());
        user.setUserPoint(0L);
        if (registerDTO.getAvatar() != null && !registerDTO.getAvatar().isEmpty()) {
            String uploadDir = "uploads/avatars/";
            String fileName = System.currentTimeMillis() + "_" + registerDTO.getAvatar().getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);

            // Tạo thư mục nếu chưa tồn tại
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Lưu file vào thư mục
            File avatarFile = new File(uploadDir + fileName);
            registerDTO.getAvatar().transferTo(avatarFile);

            // Lưu đường dẫn ảnh vào database
            user.setAvatar(uploadDir + fileName);
        }

        return usersRepository.save(user);
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
