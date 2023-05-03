package com.invogue_fashionblog.services.serviceimpl.serviceimpl;

import com.invogue_fashionblog.dto.responses.PaginationResponse;
import com.invogue_fashionblog.dto.requests.PostDto;
import com.invogue_fashionblog.dto.responses.PostResponse;
import com.invogue_fashionblog.entities.Post;
import com.invogue_fashionblog.entities.User;
import com.invogue_fashionblog.exceptions.FashionBlogApiException;
import com.invogue_fashionblog.exceptions.ResourceNotFoundException;
import com.invogue_fashionblog.repositories.PostRepository;
import com.invogue_fashionblog.repositories.UserRepository;
import com.invogue_fashionblog.services.serviceimpl.FileService;
import com.invogue_fashionblog.services.serviceimpl.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileService fileService;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, FileService fileService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.fileService = fileService;
    }

    public PostResponse createPost(PostDto postDto, Long user_id){
       log.info("create post method called");
        User user =  userRepository.findById(user_id).orElseThrow(()-> new ResourceNotFoundException("User","user_id", user_id.toString()));
       Post post = new Post();
        BeanUtils.copyProperties(postDto, post);
        post.setUser(user);
        user.getPost().add(post);
        return mapToResponse(postRepository.save(post));
    }

    /**
     * Get all posts in the database and paginate
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @Override
    public PaginationResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
      //Conditional statement to set order of sorting
       Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
               : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        //get contents per page
        List<Post> postsPerPage = posts.getContent();
        List<PostResponse> pageContent =  postsPerPage.stream().map(post -> mapToResponse(post)).collect(Collectors.toList());

        PaginationResponse paginationResponse = PaginationResponse.builder()
                .pageSize(pageSize)
                .pageNo(pageNo)
                .islastPage(posts.isLast())
                .pageContent(pageContent)
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages()).build();
        return paginationResponse;
    }

    /**
     * Get all posts made by a particular user specified by the user id
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @param user_id
     * @return
     */

    @Override
    public PaginationResponse getAllPostsByUserId(int pageNo, int pageSize, String sortBy, String sortDir, Long user_id) {
        //Conditional statement to set order of sorting
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findByUserId(user_id, pageable);
        //get contents per page
        List<Post> postsPerPage = posts.getContent();
        List<PostResponse> pageContent =  postsPerPage.stream().map(post -> mapToResponse(post)).collect(Collectors.toList());

        PaginationResponse paginationResponse = PaginationResponse.builder()
                .pageSize(pageSize)
                .pageNo(pageNo)
                .islastPage(posts.isLast())
                .pageContent(pageContent)
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages()).build();
        return paginationResponse;
    }

    @Override
    @Cacheable(cacheNames = "posts", key = "#Id")
    public PostResponse getPostById(Long Id) {
        log.info("database queried, cache missed");

        Post post = postRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", Id.toString()));

        return mapToResponse(post);
    }

    @Override
    @CachePut(cacheNames = "posts", key="#id")
    public PostResponse updatePost(PostDto post, Long id, Long user_id) {
        //Get post by id and update with the data from the request body
        Post postDB = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", id.toString()));

        if(postDB.getUser().getId() != user_id){
            throw new FashionBlogApiException(HttpStatus.FORBIDDEN, "You cannot edit this post");
        }

        postDB.setContent(post.getContent());
        postDB.setTitle(post.getTitle());
        Post updated = postRepository.save(postDB);

        return mapToResponse(updated);
    }


    @Override
    @CacheEvict(cacheNames = "posts", key = "#id")
    public ResponseEntity<String> deletePost(Long id, Long user_id) {

       Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", id.toString()));

       if(post.getUser().getId() != user_id){
           throw new FashionBlogApiException(HttpStatus.FORBIDDEN, "You cannot delete this post");
       }

        postRepository.deleteById(id);
        return ResponseEntity.ok("Post deleted");
    }

    private PostResponse mapToResponse(Post post){
        return PostResponse.builder()
                .title(post.getTitle())
                .id(post.getId())
                .file(fileService.mapToResponse(post.getFileAttachment(), post.getId()))
                .comments(post.getComments().stream().map(comment-> CommentServiceimpl.mapToCommentResponse(comment)).collect(Collectors.toSet()))
                .Like_count((long) post.getLikesList().size())
                .content(post.getContent()).build();
    }
}
