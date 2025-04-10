package com.example.project.backend.controllers;

import com.example.project.backend.dto.PostRequestDto;
import com.example.project.backend.entity.Comment;
import com.example.project.backend.entity.PostLike;
import com.example.project.backend.entity.Posts;

import com.example.project.backend.services.posts.PostServiceImpl;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {
    @Autowired
    private PostServiceImpl postService;

    @PostMapping("/add")
    public Posts createPost(
            @RequestParam("content") String content,
            @RequestParam("authorName") String authorName,
            @RequestParam("authorId") Long authorId,
            @RequestParam(value = "postImage", required = false) MultipartFile postImage) {
        return postService.createPost(content, authorName, authorId, postImage);
    }
    @GetMapping("/allpost")
    public List<Posts> getPosts() {
        return postService.getPosts();
    }

    @PostMapping("/comment/{postId}/comments")
    public Comment addComment(@PathVariable Long postId, @RequestBody Comment comment, @RequestParam String username, @RequestParam Long authorId ) {
        return postService.addComment(postId, comment, username,authorId);
    }

    @PostMapping("/like/{postId}/likes")
    public PostLike likePost(@PathVariable Long postId, @RequestBody PostLike like, @RequestParam String username,@RequestParam Long authorId ) {
        return postService.likePost(postId, like, username,authorId);
    }

    @DeleteMapping("delpost/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

    @DeleteMapping("/delcom/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        postService.deleteComment(commentId);
    }

    @PutMapping("/update/{postId}")
    public Posts updatePost(
            @PathVariable Long postId,
            @RequestParam("content") String content,
            @RequestParam("authorName") String authorName,
            @RequestParam("authorId") Long authorId,
            @RequestParam(value = "postImage", required = false) MultipartFile postImage) {
        return postService.updatePost(postId, content, authorName, authorId, postImage);
    }

}
