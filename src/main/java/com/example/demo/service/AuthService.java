package com.example.demo.service;

import com.example.demo.enums.UserRole;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService{

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public String signup(User submittedUser) {
        User user = new User();
        user.setUsername(submittedUser.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(submittedUser.getPassword()));
        user.setEmail(submittedUser.getEmail());
        user.setName(submittedUser.getName());
        user.setLastName(submittedUser.getLastName());
        user.setRole(UserRole.USER);
        userRepo.save(user);

        return "redirect:/login";
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow();
    }


}
