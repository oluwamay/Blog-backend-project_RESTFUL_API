package com.invogue_fashionblog.controller;

import com.invogue_fashionblog.dto.responses.PaginationResponse;
import com.invogue_fashionblog.dto.requests.PostDto;
import com.invogue_fashionblog.dto.responses.PostResponse;
import com.invogue_fashionblog.services.serviceimpl.PostService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fashionblog/posts")
@Slf4j
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/{user_id}")
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostDto post, @PathVariable Long user_id){
        log.info("create post end point hit");
        return new ResponseEntity<>(postService.createPost(post, user_id), HttpStatus.CREATED);
    }

    @GetMapping()
    public PaginationResponse getAllPosts(
            @RequestParam(value="pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value="sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value="sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{user_id}")
    public PaginationResponse getAllPostsById(
            @RequestParam(value="pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value="sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value="sortDir", defaultValue = "asc", required = false) String sortDir,
            @PathVariable Long user_id
    ){
        return postService.getAllPostsByUserId(pageNo, pageSize, sortBy, sortDir, user_id);
    }

    @GetMapping("/{id}/")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id){
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.FOUND);
    }

    @PutMapping("/{id}/{user_id}")
    public ResponseEntity<PostResponse> editPost(@PathVariable Long id, @Valid @RequestBody PostDto post, @PathVariable Long user_id){
        return ResponseEntity.ok(postService.updatePost(post, id, user_id));
    }

    @DeleteMapping("/{id}/{user_id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, @PathVariable Long user_id){
        return postService.deletePost(id, user_id);
    }

}
