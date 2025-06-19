package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.ArticleResponseDto;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private UserRepository userRepository;

    public ArticleResponseDto mapToDto(Article article) {
        ArticleResponseDto dto = new ArticleResponseDto();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        dto.setCreatedAt(article.getCreatedAt());

        ArticleResponseDto.AuthorDto authorDto = new ArticleResponseDto.AuthorDto();
        authorDto.setName(article.getAuthor().getName());
        dto.setAuthor(authorDto);

        ArticleResponseDto.ThemeDto themeDto = new ArticleResponseDto.ThemeDto();
        themeDto.setId(article.getTheme().getId());
        themeDto.setTitle(article.getTheme().getTitle());
        themeDto.setDescription(article.getTheme().getDescription());
        dto.setTheme(themeDto);

        return dto;
    }


    public List<ArticleResponseDto> getAllArticles() {
        return articleRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ArticleResponseDto getArticleById(Integer id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found"));

        return mapToDto(article);
    }

    public ArticleResponseDto addArticle(ArticleDto dto) {
        Theme theme = themeRepository.findById(dto.getThemeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid theme ID"));

        // Get username from principal and fetch the full User entity
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof org.springframework.security.core.userdetails.User) {
            username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authentication principal");
        }

        // Lookup User entity
        User author = userRepository.findByEmail(username)
                .orElseGet(() -> userRepository.findByName(username)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")));

        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setCreatedAt(LocalDateTime.now());
        article.setTheme(theme);
        article.setAuthor(author);

        Article savedArticle = articleRepository.save(article);
        return mapToDto(savedArticle);    }

}
