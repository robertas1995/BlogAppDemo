package com.example.demo.service;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;


import java.security.Principal;
import java.util.Collection;
import java.util.Optional;




@Service
public interface PostService {

    Optional<Post> getById(Long id);

    Collection<Post> getAll();

    Post save(Post post);

    Optional<Post> edit (Long id);

    void delete(Post post);

    String returnUsername(Principal principal);

    boolean checkIfOwner(Post post, String username);


    String createNewPost(Post post);

    String createNewPost(Post submitedPost, User user);

    void editPost(Long postId, String postTitle, String postBody);
}
