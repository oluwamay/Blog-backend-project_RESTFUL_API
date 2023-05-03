package com.invogue_fashionblog.repositories;

import com.invogue_fashionblog.entities.Likes;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    @Transactional
    @Query("SELECT l FROM Likes l WHERE l.user.id = :userId AND l.post.id = :postId")
    Likes findByUserIdAndPostId(Long userId, Long postId);


}
