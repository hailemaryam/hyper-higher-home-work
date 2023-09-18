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

    public void leaveChatroom(Long chatroomId, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatroomId).orElseThrow(() -> new RuntimeException("Chatroom not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        chatRoom.getMembers().remove(user);
        chatRoomRepository.save(chatRoom);
    }

    public Message sendMessage(Long chatroomId, Long userId, String content, String type, String filePath) {
        ChatRoom chatroom = chatRoomRepository.findById(chatroomId).orElseThrow(() -> new RuntimeException("Chatroom not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Message message = new Message();
        message.setContent(content);
        message.setType(type);
        message.setFilePath(filePath);
        message.setUser(user);
        message.setChatRoom(chatroom);
        return messageRepository.save(message);
    }

    public Message sendAttachment(Long chatroomId, Long userId, MultipartFile file) {
        String filePath = fileStorageService.storeFile(file, "attachments/");
        return sendMessage(chatroomId, userId, "Attachment", "ATTACHMENT", filePath);
    }

    public Message sendVideo(Long chatroomId, Long userId, MultipartFile video) {
        String filePath = fileStorageService.storeFile(video, "video/");
        return sendMessage(chatroomId, userId, "Video", "VIDEO", filePath);
    }

    public Message sendPicture(Long chatroomId, Long userId, MultipartFile picture) {
        String filePath = fileStorageService.storeFile(picture, "picture/");
        return sendMessage(chatroomId, userId, "Picture", "PICTURE", filePath);
    }
}
