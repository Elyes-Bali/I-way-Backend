package com.example.project.backend.services.savedPosts;

import com.example.project.backend.entity.Posts;
import com.example.project.backend.entity.SavedPost;
import com.example.project.backend.repositories.PostRepository;
import com.example.project.backend.repositories.SavedPostRepository;
import com.example.project.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedPostService {
    @Autowired
    private SavedPostRepository savedPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public void savePost(Long userId, Long postId) {
        if (!savedPostRepository.existsByUserIdAndPostId(userId, postId)) {
            SavedPost savedPost = new SavedPost();
            savedPost.setUser(userRepository.findById(userId).orElseThrow());
            savedPost.setPost(postRepository.findById(postId).orElseThrow());
            savedPostRepository.save(savedPost);
        }
    }

    public List<Posts> getSavedPosts(Long userId) {
        return savedPostRepository.findByUserId(userId)
                .stream()
                .map(SavedPost::getPost)
                .toList();
    }

    public void removeSavedPost(Long userId, Long postId) {
        savedPostRepository.deleteByUserIdAndPostId(userId, postId);
    }
}
