package com.example.demo.service;


import com.example.demo.model.Comment;
import org.springframework.stereotype.Service;

@Service
public interface CommentsService {

    Comment save(Comment comments);

    void delete(Comment comment);

}
