package com.invogue_fashionblog.controller;


import com.invogue_fashionblog.dto.requests.CommentDto;
import com.invogue_fashionblog.dto.responses.CommentResponse;
import com.invogue_fashionblog.entities.Comment;
import com.invogue_fashionblog.services.serviceimpl.CommentService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @Operation(
            description = "End point to add a comment to a post",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403")
            }
    )
    @PostMapping("/{post_id}/comments/{user_id}")
    public ResponseEntity<CommentResponse> addComment(@PathVariable Long post_id, @Valid @RequestBody CommentDto comment, @PathVariable Long user_id){
        log.info("addComment method call: "+ comment.getComment());
        return new ResponseEntity<>(commentService.createComment(post_id, comment, user_id), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all comments for a specific post",
            description = "Retrieves all comments associated with a particular post.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful response with the list of comments",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Comment.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Unauthorized or Invalid token"
                    )
            }
    )
    @GetMapping("/{post_id}/comments")
    public ResponseEntity<List<CommentResponse>> getAllComments(@PathVariable Long post_id){
        return new ResponseEntity<>(commentService.getAllComments(post_id), HttpStatus.OK);
    }
    @Hidden
    @GetMapping("/{post_id}/comments/{id}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable("post_id") Long post_id, @PathVariable("id") Long id){
        return new ResponseEntity<>(commentService.getComment(post_id, id), HttpStatus.OK);
    }
    @Operation(
            summary = "Edit a comment",
            description = "End point allows an authorized user to edit a comment made by the same user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "403", description= "Unauthorized or Invalid token")
            }
    )
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
