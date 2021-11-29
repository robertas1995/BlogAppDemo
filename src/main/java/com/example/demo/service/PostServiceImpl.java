package com.example.demo.service;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;

    @Override
    public Optional<Post> getById(Long id) {
        return postRepo.findById(id);
    }

    @Override
    public Collection<Post> getAll() {
        return postRepo.findAllByOrderByCreationDateDesc();
    }

    @Override
    public Post save(Post post) {
        return postRepo.saveAndFlush(post);
    }

    @Override
    public void delete(Post post) {
        postRepo.delete(post);
    }

    @Override
    public String returnUsername(Principal principal) {
        if(principal !=null) {
            return principal.getName();
        } else {
            return "Anonymous";
        }
    }

    @Override
    public boolean checkIfOwner(Post post, String username) {
        if (username.equals(post.getUser().getUsername())) {
            return true;
        } else return false;
    }

    @Override
    public String createNewPost(Post post) {
        return null;
    }

    @Override
    public String createNewPost(Post submitedPost, User user){

        Post newPost = new Post();
        newPost.setTitle(submitedPost.getTitle());
        newPost.setBody(submitedPost.getBody());
        newPost.setUser(user);
        newPost.getCreationDate();
        postRepo.save(newPost);

        return "redirect:/post";
    }

}

