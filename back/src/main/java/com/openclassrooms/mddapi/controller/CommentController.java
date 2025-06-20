package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.CommentRequestDto;
import com.openclassrooms.mddapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles/{articleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> addComment(
            @PathVariable Integer articleId,
            @RequestBody CommentRequestDto request
    ) {
        CommentDto dto = commentService.addComment(articleId, request.getContent());
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Integer articleId) {
        List<CommentDto> comments = commentService.getCommentsForArticle(articleId);
        return ResponseEntity.ok(comments);
    }
}
