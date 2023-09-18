package com.example.demo.service.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ChatRoomDTO {
    private String name;
    private List<Long> members;
}
