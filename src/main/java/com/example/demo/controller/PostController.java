package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepo;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final PostRepo postRepo;
    @Autowired
    public PostController(PostService postService, UserService userService, PostRepo postRepo) {
        this.postService = postService;
        this.userService = userService;
        this.postRepo = postRepo;
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id, Model model, Principal principal) {
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<Post> optionalPost = this.postService.getById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            if (authUsername.equals(post.getUser().getUsername())) {
                model.addAttribute("isOwner", true);
            }
            return "post";
        } else {
            return "error";
        }
    }
    @Secured("ROLE_USER")
    @GetMapping("/createNewPost")
    public String createNewPost(Model model) {

            Post post = new Post();
            model.addAttribute("post", post);
            return "postForm";

    }

    @Secured("ROLE_USER")
    @PostMapping("/createNewPost")
    public String createNewPost(@Valid @ModelAttribute Post post, BindingResult bindingResult, Authentication authentication) {
        //System.err.println("POST post: " + post);
        if (bindingResult.hasErrors()) {
           // System.err.println("Post did not validate");
            return "postForm";
        }
        User user = (User) authentication.getPrincipal();
        post.setUser(user);
        this.postService.save(post);
//        sessionStatus.setComplete();
        return "redirect:/post/" + post.getId();
    }

    @Secured("ROLE_USER")
    @GetMapping("/editPost/{postId}")
    public String editPost(@PathVariable Long postId, Model model) {
        model.addAttribute("postId",postId);
        var post = postRepo.getById(postId);
        model.addAttribute("editpost",post);
        return "/editPost";

    }

    @PostMapping("/editPost/{postId}")
    public String editPost(@PathVariable Long postId,@RequestParam(name = "postTitle") String postTitle, @RequestParam(name = "postBody") String postBody) {
        postService.editPost(postId, postTitle, postBody);

//        sessionStatus.setComplete();
        return "redirect:/home";
    }


    @Secured("ROLE_USER")
    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable Long id, Principal principal) {


        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }

        Optional<Post> optionalPost = this.postService.getById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if (authUsername.equals(post.getUser().getUsername())) {

                this.postService.delete(post);
                //System.err.println("DELETED post: " + post);
                return "redirect:/home";
            } else {
               // System.err.println("Current User has no permissions to edit anything on post by id: " + id);
                return "403";
            }
        } else {
           // System.err.println("Could not find a post by id: " + id);
            return "error";
        }
    }

}

