package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

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


    @Override
    public void editComment(Long commentId, String commentBody) {
        var comment = commentRepo.findById(commentId).orElseThrow(()-> new EntityNotFoundException("Comment not found"));
        comment.setBody(commentBody);
        commentRepo.save(comment);
    }

    @Override
    public Optional<Comment> getById(Long id) {
        return commentRepo.findById(id);
    }
}
