package com.osb.youtube.controller;

import com.osb.youtube.dto.request.AddCommentRequest;
import com.osb.youtube.dto.response.CommentResponse;
import com.osb.youtube.service.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<String> addComment(
            @RequestBody AddCommentRequest request) {
        commentService.addComment(request);
        return ResponseEntity.ok("Comment added successfully");
    }

    @GetMapping("/video/{videoId}")
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable String videoId) {
        return ResponseEntity.ok(
                commentService.getComments(videoId)
        );
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable String commentId,
            @RequestParam String text) {
        commentService.updateComment(commentId, text);
        return ResponseEntity.ok("Comment updated successfully");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable String commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}