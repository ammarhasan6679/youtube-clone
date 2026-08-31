package com.osb.youtube.repository;

import com.osb.youtube.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findByVideoIdAndParentCommentIsNull(String videoId);
    long countByVideoId(String videoId);
}
