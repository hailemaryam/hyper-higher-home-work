package com.example.demo.controller;

import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.Message;
import com.example.demo.service.ChatRoomService;
import com.example.demo.service.DTO.ChatRoomDTO;
import com.example.demo.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api/chatrooms")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<ChatRoom> createChatroom(@RequestBody ChatRoomDTO chatroomDTO) {
        return ResponseEntity.ok(chatRoomService.createChatroom(chatroomDTO));
    }
}

