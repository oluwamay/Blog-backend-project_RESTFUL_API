package com.invogue_fashionblog.controller;


import com.invogue_fashionblog.dto.requests.CommentDto;
import com.invogue_fashionblog.dto.responses.CommentResponse;
import com.invogue_fashionblog.services.serviceimpl.CommentService;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fashionblog/posts")
@Slf4j
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{post_id}/comments/{user_id}")
    public ResponseEntity<CommentResponse> addComment(@PathVariable Long post_id, @Valid @RequestBody CommentDto comment, @PathVariable Long user_id){
        log.info("addComment method call: "+ comment.getComment());
        return new ResponseEntity<>(commentService.createComment(post_id, comment, user_id), HttpStatus.CREATED);
    }

    @GetMapping("/{post_id}/comments")
    public ResponseEntity<List<CommentResponse>> getAllComments(@PathVariable Long post_id){
        return new ResponseEntity<>(commentService.getAllComments(post_id), HttpStatus.FOUND);
    }
    @GetMapping("/{post_id}/comments/{id}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable("post_id") Long post_id, @PathVariable("id") Long id){
        return new ResponseEntity<>(commentService.getComment(post_id, id), HttpStatus.FOUND);
    }

    @PutMapping("/{post_id}/comments/{id}/{user_id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable("post_id")  Long post_id,
                                                         @PathVariable("id") Long id,
                                                         @Valid @RequestBody CommentDto comment,
                                                         @PathVariable Long user_id){
        return new ResponseEntity<>(commentService.updateComment(post_id, id, comment, user_id), HttpStatus.OK);
    }

    @DeleteMapping("{post_id}/comments/{id}/{user_id}")
    public ResponseEntity<String> deleteComment(@PathVariable("post_id")  Long post_id,
                                                @PathVariable("id") Long id,
                                                @PathVariable Long user_id
    ){
        return commentService.deleteComment(post_id, id, user_id);
    }
}
