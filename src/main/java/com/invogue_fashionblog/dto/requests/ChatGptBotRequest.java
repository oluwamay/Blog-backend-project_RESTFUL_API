package com.invogue_fashionblog.dto.requests;

import com.invogue_fashionblog.dto.Message;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatGptBotRequest {

    private String model;

    private List<Message> messages;

    public ChatGptBotRequest(String model, String prompt){
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add( new Message("user", prompt));
    }
}
