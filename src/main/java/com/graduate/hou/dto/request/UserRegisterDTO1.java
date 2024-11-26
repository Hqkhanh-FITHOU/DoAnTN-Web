package com.graduate.hou.dto.request;

import com.graduate.hou.enums.RoleUsers;
import lombok.*;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegisterDTO1 {
    private String username;

    private String password;

    private String email;

    private String phone;

    private Set<RoleUsers> roles;

    private String fullname;

    private MultipartFile avatar;
}
