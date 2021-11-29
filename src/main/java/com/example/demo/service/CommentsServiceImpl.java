package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class CommentsServiceImpl implements CommentsService{

    private CommentRepo commentRepo;

    @Autowired
    public void CommentServiceImpl(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }


    @Override
    public Comment save(Comment comment) {
        return commentRepo.saveAndFlush(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentRepo.delete(comment);
    }
}
