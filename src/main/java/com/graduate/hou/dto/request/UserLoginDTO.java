package com.graduate.hou.dto.request;

import lombok.Getter;
import org.hibernate.annotations.processing.Pattern;

@Getter
public class UserLoginDTO {
    private String username;

    private String password;
}
