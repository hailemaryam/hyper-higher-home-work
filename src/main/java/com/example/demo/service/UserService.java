package com.example.demo.service;

import com.example.demo.entity.ChatUser;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ChatUser createUser(UserDTO userDTO){
        ChatUser user = new ChatUser();
        user.setUsername(userDTO.getUserName());
        return userRepository.save(user);
    }
}
