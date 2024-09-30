package com.graduate.hou.dto;

import com.graduate.hou.enums.RoleUsers;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.security.Timestamp;

public class UsersDTO {
    private Long userId;

    private String username;

    private String password;

    private String email;

    private String phone;

    private RoleUsers role;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
