package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.PostService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.core.Authentication;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private PostController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void ContexLoad() throws Exception{
        assertThat(controller).isNotNull();


    }
    @LocalServerPort
    private int port;


    @Test
    public void postExample() throws Exception {


    }



    @Test
    void createNewPost() {
    }

    @Test
    void testCreateNewPost() {
    }

    @Test
    void editPost() {
    }

    @Test
    void deletePost() {
    }
}