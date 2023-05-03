package com.invogue_fashionblog.controller;

import com.invogue_fashionblog.dto.responses.LikeResponse;
import com.invogue_fashionblog.services.serviceimpl.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fashionblog/posts")
@Slf4j
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{post_id}/like/{user_id}")
    public ResponseEntity<LikeResponse> likePost(@PathVariable Long post_id, @PathVariable Long user_id){
       log.info(post_id+" "+user_id);
        return likeService.likePost(post_id, user_id);
    }

}
