package com.example.demo.controller;

import com.example.demo.dto.RegistrationRequest;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class RegisterController {
    @Autowired
    private AuthService authService;

    @GetMapping("/register")
    public String registrationView(Model model){
        model.addAttribute("user", new User());

        return "registerForm";
    }


    @PostMapping("/register")
    public String createNewUser(@ModelAttribute User user){

        return authService.signup(user);
    }

}
