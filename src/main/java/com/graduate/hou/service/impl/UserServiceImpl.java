package com.graduate.hou.service.impl;

import com.graduate.hou.dto.UsersDTO;
import com.graduate.hou.entity.User;
import com.graduate.hou.repository.UsersRepository;
import com.graduate.hou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<User> getAllUser() {
        return usersRepository.findAll();
    }

    @Override
    public User createUser(UsersDTO usersDTO) {
        User user = new User();

        user.setUsername(usersDTO.getUsername());
        user.setPassword(usersDTO.getPassword());
        user.setEmail(usersDTO.getEmail());
        user.setPhone(usersDTO.getPhone());
        user.setRole(usersDTO.getRole());
        user.setCreatedAt(usersDTO.getCreatedAt());
        user.setUpdatedAt(usersDTO.getUpdatedAt());

        return usersRepository.save(user);
    }

    @Override
    public User updateUser(Long id, UsersDTO usersDTO) {
        Optional<User> optionalUser = usersRepository.findById(id);
        User user = optionalUser.get();

        user.setUsername(usersDTO.getUsername());
        user.setPassword(usersDTO.getPassword());
        user.setEmail(usersDTO.getEmail());
        user.setPhone(usersDTO.getPhone());
        user.setRole(usersDTO.getRole());
        user.setCreatedAt(usersDTO.getCreatedAt());
        user.setUpdatedAt(usersDTO.getUpdatedAt());

        return usersRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}
