package com.invogue_fashionblog.services.serviceimpl;

import com.invogue_fashionblog.dto.responses.PaginationResponse;
import com.invogue_fashionblog.dto.requests.PostDto;
import com.invogue_fashionblog.dto.responses.PostResponse;
import org.springframework.http.ResponseEntity;

public interface PostService {
    PostResponse createPost(PostDto postDto, Long user_id);

    PaginationResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PaginationResponse getAllPostsByUserId(int pageNo, int pageSize, String sortBy, String sortDir, Long user_id);

    PostResponse getPostById(Long Id);

    PostResponse updatePost(PostDto post, Long id, Long user_id);

    ResponseEntity<String> deletePost(Long id, Long user_id);
}
