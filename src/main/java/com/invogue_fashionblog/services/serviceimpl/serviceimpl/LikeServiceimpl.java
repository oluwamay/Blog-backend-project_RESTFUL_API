package com.invogue_fashionblog.services.serviceimpl.serviceimpl;

import com.invogue_fashionblog.dto.responses.LikeResponse;
import com.invogue_fashionblog.entities.Likes;
import com.invogue_fashionblog.entities.Post;
import com.invogue_fashionblog.entities.User;
import com.invogue_fashionblog.exceptions.ResourceNotFoundException;
import com.invogue_fashionblog.repositories.LikeRepository;
import com.invogue_fashionblog.repositories.PostRepository;
import com.invogue_fashionblog.repositories.UserRepository;
import com.invogue_fashionblog.services.serviceimpl.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LikeServiceimpl implements LikeService {
    private final LikeRepository likeRepository;
    private  final PostRepository postRepository;
    private final UserRepository userRepository;

    public LikeServiceimpl(LikeRepository likeRepository, PostRepository postRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<LikeResponse> likePost(Long postId, Long user_id) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post id", postId.toString()));
        User user = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", "User_id", user_id.toString()));
        Likes existingLike = likeRepository.findByUserIdAndPostId(user_id, postId);

        if(existingLike != null){
            likeRepository.delete(existingLike);
            return new ResponseEntity<>(LikeResponse.builder().like_id(postId).message("Unliked post").build(), HttpStatus.CREATED);
        }

            Likes like = likeRepository.save(Likes.builder().post(post).user(user).build());
            post.addLike(like);
        return new ResponseEntity<>(LikeResponse.builder().like_id(like.getId()).message("Liked post").build(), HttpStatus.CREATED);
    }

}
