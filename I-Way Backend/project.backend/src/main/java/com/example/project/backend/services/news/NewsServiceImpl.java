package com.example.project.backend.services.news;

import com.example.project.backend.configurations.CloudinaryService;
import com.example.project.backend.entity.News;
import com.example.project.backend.entity.Posts;
import com.example.project.backend.repositories.NewsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class NewsServiceImpl implements NewsService{
    @Autowired
    private NewsRepository newsRepository;


    @Autowired
    private CloudinaryService cloudinaryService;

    public News createNews(String title, String content, MultipartFile newsImage) {
        News news = new News();
        news.setTitle(title);
        news.setContent(content);

        // Handle image upload if provided
        if (newsImage != null && !newsImage.isEmpty()) {
            try {
                byte[] imageBytes = newsImage.getBytes();
                String imageUrl = cloudinaryService.uploadImage(imageBytes);  // This method will upload the image
                news.setNewsImage(imageUrl);  // Save the image URL in post
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        }

        // Save the post in the database
        return newsRepository.save(news);
    }


    public List<News> getNews() {
        return newsRepository.findAll();
    }


    @Transactional
    public void deleteNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found"));
        newsRepository.delete(news);
    }
}
