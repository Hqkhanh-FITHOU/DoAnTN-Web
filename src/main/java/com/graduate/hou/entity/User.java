package com.graduate.hou.entity;

import com.graduate.hou.enums.RoleUsers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tbl_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 100)
    private String fullname;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(length = 15, unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleUsers role;

    @Column
    private Long userPoint;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    List<Address> addresses;

    @OneToMany(mappedBy = "user")
    List<Notification> notifications;

    @OneToMany(mappedBy = "user")
    List<Order> orders;

    @OneToMany(mappedBy = "user")
    List<Review> reviews;

    @OneToMany(mappedBy = "user")
    List<Response> respone;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
    
}
