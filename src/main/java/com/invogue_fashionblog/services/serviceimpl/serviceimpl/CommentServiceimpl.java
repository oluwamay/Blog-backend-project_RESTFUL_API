package com.invogue_fashionblog.services.serviceimpl.serviceimpl;

import com.invogue_fashionblog.dto.requests.CommentDto;
import com.invogue_fashionblog.dto.responses.CommentResponse;
import com.invogue_fashionblog.entities.Comment;
import com.invogue_fashionblog.entities.Post;
import com.invogue_fashionblog.entities.User;
import com.invogue_fashionblog.exceptions.FashionBlogApiException;
import com.invogue_fashionblog.exceptions.ResourceNotFoundException;
import com.invogue_fashionblog.repositories.CommentRepository;
import com.invogue_fashionblog.repositories.PostRepository;
import com.invogue_fashionblog.repositories.UserRepository;
import com.invogue_fashionblog.services.serviceimpl.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceimpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentServiceimpl(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentResponse createComment(Long post_id, CommentDto comment, Long user_id) {
        log.info("create Comment called");
        Comment commentDB = new Comment();
        User user = userRepository.findById(user_id).orElseThrow(()->new ResourceNotFoundException("User","USer Id", user_id.toString()));
        Post post = postRepository.findById(post_id).orElseThrow(()->new ResourceNotFoundException("Post", "Post_id", post_id.toString()));

        BeanUtils.copyProperties(comment, commentDB);

        post.addComment(commentDB);
        commentDB.setPost(post);
        commentDB.setUser(user);

        log.info("New Comment created with post id: "+ commentDB.getPost().getId());
        return mapToCommentResponse(commentRepository.save(commentDB), user);
    }

    @Override
    public List<CommentResponse> getAllComments(Long postId) {
        return commentRepository.findAllByPostId(postId).stream().map(comment -> mapToCommentResponse(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentResponse getComment(Long postId, Long id) {
       Comment comment = commentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Comment","comment_id", id.toString()));
        return mapToCommentResponse(comment);
    }

    @Override
    public CommentResponse updateComment(Long postId, Long id, CommentDto comment, Long user_id) {
        Comment commentDB = validatePostComment(postId, id, user_id);
        commentDB.setComment(comment.getComment());
        return mapToCommentResponse(commentRepository.save(commentDB), userRepository.findById(user_id).get());
    }

    @Override
    public ResponseEntity<String> deleteComment(Long postId, Long id, Long user_id) {
        Comment comment = validatePostComment(postId, id, user_id);
        commentRepository.deleteById(comment.getId());
        return ResponseEntity.ok("Comment deleted");
    }

    public static CommentResponse mapToCommentResponse(Comment comment, User user) {
        return CommentResponse.builder()
                .comment(comment.getComment())
                .email(user.getEmail())
                .id(comment.getId()).build();
    }
    public static CommentResponse mapToCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .comment(comment.getComment())
                .email(comment.getUser().getEmail())
                .id(comment.getId()).build();
    }

    private Comment validatePostComment(Long post_Id, Long id, Long user_id){
        Post post;
        post = postRepository.findById(post_Id).orElseThrow(()->new ResourceNotFoundException("Post", "Post_id", post_Id.toString()));
        Comment commentDB = commentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment","comment_id", id.toString()));

        if(!commentDB.getPost().getId().equals(post.getId())){
            throw new FashionBlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to this post");
        }
        if(user_id != commentDB.getUser().getId()){
            throw new FashionBlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to this user");
        }
        return commentDB;
    }

}
