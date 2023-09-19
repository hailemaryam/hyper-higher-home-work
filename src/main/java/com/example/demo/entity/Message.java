package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String type; // TEXT, VIDEO, ATTACHMENT
    private String filePath;
    @ManyToOne
    private ChatUser user;
    @ManyToOne
    private ChatRoom chatRoom;
    private Instant createdAt;
    private Instant updatedAt;
}
