package com.graduate.hou.dto.request;

import com.graduate.hou.enums.RoleUsers;
import lombok.*;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegisterDTO {

    private String username;

    private String password;

    private String email;

    private String phone;

    private Set<RoleUsers> roles;

    private String fullname;
}
