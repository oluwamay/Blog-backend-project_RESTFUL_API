package com.invogue_fashionblog.services.serviceimpl;

import com.invogue_fashionblog.dto.responses.LikeResponse;
import org.springframework.http.ResponseEntity;

public interface LikeService {
    ResponseEntity<LikeResponse> likePost(Long postId, Long user_id);

}
