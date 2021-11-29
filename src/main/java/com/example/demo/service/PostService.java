package com.example.demo.service;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.security.Principal;
import java.util.Collection;
import java.util.Optional;




@Service
public interface PostService {

    Optional<Post> getById(Long id);

    Collection<Post> getAll();

    Post save(Post post);

    void delete(Post post);

    String returnUsername(Principal principal);

    boolean checkIfOwner(Post post, String username);


    String createNewPost(Post post);

    String createNewPost(Post submitedPost, User user);
}
