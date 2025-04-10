package com.example.project.backend.dto;

import com.example.project.backend.entity.Posts;
import lombok.Data;

@Data
public class PostRequestDto {
    public static class PostRequest {
        private String content;
        private String authorName;
        private Long authorId;

        // Getters and setters
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public Long getAuthorId() {
            return authorId;
        }

        public void getAuthorId(Long authorId) {
            this.authorId = authorId;
        }

        public Posts getPost() {
            Posts post = new Posts();
            post.setContent(this.content);
            return post;
        }
    }
}
