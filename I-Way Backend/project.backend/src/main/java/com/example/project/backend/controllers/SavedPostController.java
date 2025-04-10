package com.example.project.backend.controllers;
import com.example.project.backend.entity.Posts;
import com.example.project.backend.services.savedPosts.SavedPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/saved-posts")
@CrossOrigin(origins = "http://localhost:4200")
public class SavedPostController {
    @Autowired
    private SavedPostService savedPostService;

    @PostMapping("add/{userId}/{postId}")
    public ResponseEntity<Map<String, String>> savePost(@PathVariable Long userId, @PathVariable Long postId) {
        savedPostService.savePost(userId, postId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Post saved");
        return ResponseEntity.ok(response);
    }

    @GetMapping("all/{userId}")
    public ResponseEntity<List<Posts>> getSavedPosts(@PathVariable Long userId) {
        return ResponseEntity.ok(savedPostService.getSavedPosts(userId));
    }

    @DeleteMapping("delete/{userId}/{postId}")
    public ResponseEntity<?> removeSavedPost(@PathVariable Long userId, @PathVariable Long postId) {
        savedPostService.removeSavedPost(userId, postId);
        return ResponseEntity.ok("Post removed from saved");
    }
}
