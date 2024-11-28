package com.graduate.hou.dto.request;

import com.graduate.hou.enums.RoleUsers;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
    private Long userId;

    private String username;

    private String password;

    private String email;

    private String phone;

    private Set<RoleUsers> roles;

    private String fullname;

    private Long point;

    private MultipartFile avatar;
}
