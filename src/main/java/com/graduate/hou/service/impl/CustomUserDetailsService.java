package com.graduate.hou.service.impl;

import java.util.Collection;
import java.util.HashSet;
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
        User user = userService.findByUserName(username).get();
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        log.info(username);
        Collection<GrantedAuthority> authorities = new HashSet<>();
        Set<RoleUsers> roles = user.getRoles();
        for(RoleUsers role : roles){
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        log.info(roles.toString());
        return new CustomUserDetails(user, authorities);
    }

}
