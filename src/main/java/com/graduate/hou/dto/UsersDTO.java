package com.graduate.hou.dto;

import com.graduate.hou.enums.RoleUsers;
import lombok.*;

import java.security.Timestamp;

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

    private RoleUsers role;

    private String fullname;
}
