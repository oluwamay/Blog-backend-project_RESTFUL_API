package com.invogue_fashionblog.repositories;

import com.invogue_fashionblog.entities.Comment;
import com.invogue_fashionblog.entities.Likes;
import com.invogue_fashionblog.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUserId(Long userId, Pageable pageable);
}
