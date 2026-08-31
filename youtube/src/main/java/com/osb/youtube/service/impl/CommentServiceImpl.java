package com.osb.youtube.service.impl;

import com.osb.youtube.dto.request.AddCommentRequest;
import com.osb.youtube.dto.response.CommentResponse;
import com.osb.youtube.entity.Comment;
import com.osb.youtube.entity.User;
import com.osb.youtube.entity.Video;
import com.osb.youtube.repository.CommentRepository;
import com.osb.youtube.repository.UserRepository;
import com.osb.youtube.repository.VideoRepository;
import com.osb.youtube.service.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final CommentRepository commentRepository;
    @Override
    public void addComment(AddCommentRequest request) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String userName = authentication.getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(()
                        -> new RuntimeException("User not found"));
        Video video = videoRepository.findById(request.getVideoId())
                .orElseThrow(()
                        -> new RuntimeException("Video not found"));
        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setUser(user);
        comment.setVideo(video);
        if(request.getParentCommentId() != null) {
            Comment parentComment = commentRepository
                    .findById(request.getParentCommentId()).orElseThrow(()
                            -> new RuntimeException("Parent comment not found"));
            comment.setParentComment(parentComment);
        }
        commentRepository.save(comment);
    }

    @Override
    public List<CommentResponse> getComments(String videoId) {
        List<Comment> allComment = commentRepository
                .findByVideoIdAndParentCommentIsNull(videoId);
        return allComment.stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public void updateComment(String commentId, String text) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can update only your own comment");
        }
        comment.setText(text);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(String commentId) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can delete only your own comment");
        }
        commentRepository.delete(comment);
    }

    private CommentResponse convertToResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setText(comment.getText());
        response.setUsername(comment.getUser().getUserName());
        List<CommentResponse> replies = comment.getReplies()
                .stream()
                .map(this::convertToResponse)
                .toList();
        response.setReplies(replies);
        return response;
    }
}
