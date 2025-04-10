package com.example.project.backend.services.posts;

import com.example.project.backend.configurations.CloudinaryService;
import com.example.project.backend.entity.Comment;
import com.example.project.backend.entity.PostLike;


import com.example.project.backend.entity.Posts;
import com.example.project.backend.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PostServiceImpl {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostLikeRepository likeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SavedPostRepository savedPostRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    public Posts createPost(String content, String authorName, Long authorId, MultipartFile postImage) {
        Posts post = new Posts();
        post.setContent(content);
        post.setAuthor(authorName);
        post.setAuthorid(authorId);

        // Handle image upload if provided
        if (postImage != null && !postImage.isEmpty()) {
            try {
                byte[] imageBytes = postImage.getBytes();
                String imageUrl = cloudinaryService.uploadImage(imageBytes);  // This method will upload the image
                post.setPostImage(imageUrl);  // Save the image URL in post
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        }

        // Save the post in the database
        return postRepository.save(post);
    }

    // Get all posts
    public List<Posts> getPosts() {
        return postRepository.findAll();
    }

    // Add a comment to a post
    public Comment addComment(Long postId, Comment comment, String authorName,Long authorId) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        comment.setPost(post);
        comment.setAuthorid(authorId);

        // Set the author directly from frontend data
        comment.setAuthor(authorName);

        return commentRepository.save(comment);
    }

    // Like a post
    public PostLike likePost(Long postId, PostLike like, String username,Long authorId) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Set the user directly from frontend data
        like.setUser(username);
        like.setPost(post);
        like.setAuthorid(authorId);

        return likeRepository.save(like);
    }
    @Transactional
    public void deletePost(Long postId) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Remove saved post references before deleting the post
        savedPostRepository.deleteByPostId(postId);

        // Now delete the post
        postRepository.delete(post);
    }


    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.delete(comment);
    }


    public Posts updatePost(Long postId, String content, String authorName, Long authorId, MultipartFile postImage) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Update the content and author information
        post.setContent(content);
        post.setAuthor(authorName);
        post.setAuthorid(authorId);

        // Handle image upload if provided
        if (postImage != null && !postImage.isEmpty()) {
            try {
                byte[] imageBytes = postImage.getBytes();
                String imageUrl = cloudinaryService.uploadImage(imageBytes);  // This method will upload the image
                post.setPostImage(imageUrl);  // Save the image URL in post
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        }

        // Save the updated post to the database
        return postRepository.save(post);
    }


}
