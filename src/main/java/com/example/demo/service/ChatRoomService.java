package com.example.demo.service;

import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.repository.ChatRoomRepository;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.DTO.ChatRoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ChatRoomService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    public ChatRoom createChatroom(ChatRoomDTO chatRoomDTO) {
        ChatRoom chatroom = new ChatRoom();
        chatroom.setName(chatRoomDTO.getName());
        Set<User> members = new HashSet<>();
        chatRoomDTO.getMembers().forEach(memberId -> {
            Optional<User> byId = userRepository.findById(memberId);
            if (byId.isPresent()){
                members.add(byId.get());
            }
        });
        chatroom.setMembers(members);
        return chatRoomRepository.save(chatroom);
    }
}
