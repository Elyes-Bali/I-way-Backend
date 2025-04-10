package com.example.project.backend.repositories;

import com.example.project.backend.entity.PostLike;
import com.example.project.backend.entity.Posts;
import com.example.project.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Posts post);
}
