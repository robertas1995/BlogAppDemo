package com.example.demo.dto;

import lombok.Data;

@Data
public class RegistrationRequest {

    private String username;
    private String password;
    private String email;
    private String name;
    private String lastName;


}
