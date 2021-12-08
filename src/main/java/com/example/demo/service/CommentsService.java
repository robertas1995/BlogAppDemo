package com.example.demo.service;


import com.example.demo.model.Comment;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CommentsService {

    Comment save(Comment comments);

    void delete(Comment comment);

    void editComment(Long commentId, String commentBody);

    Optional<Comment> getById(Long id);
}
