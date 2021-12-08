package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepo;
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
public class CommentController {

    private final PostService postService;
    private final UserService userService;
    private final CommentsService commentsService;
    private final CommentRepo commentRepo;

    @Autowired
    public CommentController(PostService postService, UserService userService, CommentsService commentsService, CommentRepo commentRepo) {
        this.postService = postService;
        this.userService = userService;
        this.commentsService = commentsService;
        this.commentRepo = commentRepo;
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
    public String validateComment(@Valid @ModelAttribute Comment comment, BindingResult bindingResult) {
        //System.err.println("POST comment: " + comment);
        if (bindingResult.hasErrors()) {
            //System.err.println("Comment did not validate");
            return "commentForm";
        } else {
            this.commentsService.save(comment);

            return "redirect:/post/" + comment.getPost().getId();
        }
    }


    @GetMapping("/editComment/{commentId}")
    public String editComment(@PathVariable Long commentId, Model model) {
        model.addAttribute("commentId", commentId);
        System.out.println("GAVAU COMMENTID"+ commentId);
        var comment = commentRepo.getById(commentId);
        model.addAttribute("editcomment", comment);
        return "/editComment";
    }

    @PostMapping("/editComment/{commentId}")
    public String editComment(@PathVariable Long commentId, @RequestParam(name = "body") String commentBody) {
        commentsService.editComment(commentId, commentBody);

        return "redirect:/home";
    }

    @Secured("ROLE_USER")
    @GetMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable Long id, Principal principal) {

        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<Comment> optionalComment = this.commentsService.getById(id);

        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();

            if (authUsername.equals(comment.getUser().getUsername())) {

                this.commentsService.delete(comment);

                return "redirect:/home";

            } else {
                return "403";
            }
        } else {
            return "error";
        }
    }

}