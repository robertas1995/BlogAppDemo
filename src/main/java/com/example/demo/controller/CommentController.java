package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.CommentsService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@SessionAttributes("comment")
public class CommentController {

    private final PostService postService;
    private final UserService userService;
    private final CommentsService commentsService;

    @Autowired
    public CommentController(PostService postService, UserService userService, CommentsService commentsService) {
        this.postService = postService;
        this.userService = userService;
        this.commentsService = commentsService;
    }

    @Secured("ROLE_USER")
    @GetMapping("/comment/{id}")
    public String showComment(@PathVariable Long id, Model model, Principal principal) {


        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<User> optionalBlogUser = this.userService.findByUsername(authUsername);

        Optional<Post> postOptional = this.postService.getById(id);

        if (postOptional.isPresent() && optionalBlogUser.isPresent()) {
            Comment comment = new Comment();
            comment.setPost(postOptional.get());
            comment.setUser(optionalBlogUser.get());
            model.addAttribute("comment", comment);
            //System.err.println("GET comment/{id}: " + comment + "/" + id);
            return "commentForm";
        } else {
            //System.err.println("Could not find a post by id: " + id + " or user by logged in username: " + authUsername);
            return "error";
        }
    }

    @Secured("ROLE_USER")
    @PostMapping("/comment")
    public String validateComment(@Valid @ModelAttribute Comment comment, BindingResult bindingResult, SessionStatus sessionStatus) {
        //System.err.println("POST comment: " + comment);
        if (bindingResult.hasErrors()) {
            //System.err.println("Comment did not validate");
            return "commentForm";
        } else {
            this.commentsService.save(comment);
           // System.err.println("SAVE comment: " + comment);
            sessionStatus.setComplete();
            return "redirect:/post/" + comment.getPost().getId();
        }
    }

}

