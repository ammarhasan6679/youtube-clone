package com.osb.youtube.service.interfaces;

import com.osb.youtube.dto.request.AddCommentRequest;
import com.osb.youtube.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {
    void addComment(AddCommentRequest request);

    List<CommentResponse> getComments(String videoId);

    void updateComment(String commentId, String text);

    void deleteComment(String commentId);
}
