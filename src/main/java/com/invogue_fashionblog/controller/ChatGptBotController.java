package com.invogue_fashionblog.controller;

import com.invogue_fashionblog.dto.requests.ChatGptBotRequest;
import com.invogue_fashionblog.dto.responses.ChatGptResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/fashionblog")
@RequiredArgsConstructor
public class ChatGptBotController {

    private final RestTemplate template;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String url;

    @GetMapping("/chat-bot")
    public String chat(@RequestParam("prompt") String prompt){
        ChatGptBotRequest request = new ChatGptBotRequest(model, prompt);
        ChatGptResponse response = template.postForObject(url, request, ChatGptResponse.class);
        return response.getChoices().get(0).getMessage().getContent();
    }
}
