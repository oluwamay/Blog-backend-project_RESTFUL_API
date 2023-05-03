package com.invogue_fashionblog.services.serviceimpl;

import com.invogue_fashionblog.dto.requests.CommentDto;
import com.invogue_fashionblog.dto.responses.CommentResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(Long post_id, CommentDto comment, Long user_id);

    List<CommentResponse> getAllComments(Long postId);

    CommentResponse getComment(Long postId, Long id);

    CommentResponse updateComment(Long postId, Long id, CommentDto comment, Long user_id);

    ResponseEntity<String> deleteComment(Long postId, Long id, Long user_id);
}
