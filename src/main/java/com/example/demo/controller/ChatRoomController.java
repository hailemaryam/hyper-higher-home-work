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

    @PostMapping("/{chatroomId}/leave")
    public ResponseEntity<Void> leaveChatroom(@PathVariable Long chatroomId, @RequestParam Long userId) {
        chatRoomService.leaveChatroom(chatroomId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{chatroomId}/send/attachment")
    public ResponseEntity<Message> sendAttachment(@PathVariable Long chatroomId, @RequestParam Long userId, @RequestParam("file") MultipartFile file) {
        Message message = chatRoomService.sendAttachment(chatroomId, userId, file);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/{chatroomId}/send/video")
    public ResponseEntity<Message> sendVideo(@PathVariable Long chatroomId, @RequestParam Long userId, @RequestParam("video") MultipartFile video) {
        Message message = chatRoomService.sendVideo(chatroomId, userId, video);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/{chatroomId}/send/picture")
    public ResponseEntity<Message> sendPicture(@PathVariable Long chatroomId, @RequestParam Long userId, @RequestParam("picture") MultipartFile picture) {
        Message message = chatRoomService.sendPicture(chatroomId, userId, picture);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}

