package com.example.project.backend.controllers;

import com.example.project.backend.entity.News;
import com.example.project.backend.services.news.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "http://localhost:4200")
public class NewsController {
    private final NewsServiceImpl newsService;

    // Constructor-based Dependency Injection
    @Autowired
    public NewsController(NewsServiceImpl newsService) {
        this.newsService = newsService;
    }

    @PostMapping("/add")
    public News createPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "newsImage", required = false) MultipartFile newsImage) {
        return newsService.createNews(title, content, newsImage);
    }

    @GetMapping("/allnews")
    public List<News> getNews() {
        return newsService.getNews();
    }

    @DeleteMapping("delNews/{id}")
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
    }
}
