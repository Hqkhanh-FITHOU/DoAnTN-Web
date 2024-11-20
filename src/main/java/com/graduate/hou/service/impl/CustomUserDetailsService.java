package com.graduate.hou.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.graduate.hou.entity.CustomUserDetails;
import com.graduate.hou.entity.User;
import com.graduate.hou.enums.RoleUsers;
import com.graduate.hou.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userService.findByUserName(username)
                                .orElseGet(() -> userService.findByPhone(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or phone: " + username))));
        log.info(user.get().toString());
        Collection<GrantedAuthority> authorities = new HashSet<>();
        Set<RoleUsers> roles = user.get().getRoles();
        for(RoleUsers role : roles){
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        log.info(roles.toString());
        return new CustomUserDetails(user.get(), authorities);
    }

}
