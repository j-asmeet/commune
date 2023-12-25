package com.group6.commune.ControllerTests;

import com.group6.commune.Controller.CommunityController;
import com.group6.commune.Controller.PostController;
import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.Community;
import com.group6.commune.Model.CommunityPosts;
import com.group6.commune.Model.Event;
import com.group6.commune.Service.CommunityPostServiceImpl;
import com.group6.commune.Service.CommunityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CommunityPostServiceImpl communityPostService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    public void testCreatePost() throws Exception {
        CommunityPosts posts = new CommunityPosts();
        posts.setPostId(1);
        posts.setPostTitle("Test");

        given(communityPostService.createPosts(any(CommunityPosts.class), any(BindingResult.class))).willReturn(posts);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"postId\":1,\"postTitle\":\"Test\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.postId").value(1))
                .andExpect(jsonPath("$.postTitle").value("Test"));
    }

//    @Test
//    public void createPostForInvalidDataTest() throws Exception {
//        CommunityPosts posts = new CommunityPosts();
//        posts.setPostId(1);
//        posts.setPostTitle("");
//
//        Map<String, String> errors = new HashMap<>();
//        errors.put("postTitle", "Post Name should not be empty or null.");
//
//        given(communityPostService.createPosts(any(CommunityPosts.class), any(BindingResult.class)))
//                .willThrow(new ValidationException("Validation failed", errors));
//
//        mockMvc.perform(post("/posts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"postId\":1,\"postTitle\":\"\"}"))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.message").value("Validation failed"))
//                .andExpect(jsonPath("$.errors.postTitle").value("Post Name should not be empty or null."));
//    }

//    @Test
//    public void deletePostForRecordDoesNotExistTest() throws Exception {
//        given(communityPostService.deletePosts(1)).willThrow(new DataNotFoundException("Post with ID: 1 not found"));
//
//        mockMvc.perform(delete("/posts/1"))
//                .andExpect(status().isNotFound())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.message").value("Post with ID: 1 not found"));
//    }

    @Test
    public void getPostByIDTest() throws Exception {
        CommunityPosts posts = new CommunityPosts();
        posts.setPostId(1);
        posts.setPostTitle("Test");

        given(communityPostService.getPostById(1)).willReturn(posts);

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.postId").value(1))
                .andExpect(jsonPath("$.postTitle").value("Test"));
    }

//    @Test
//    public void getpostByIdWhichDoesNotExistTest() throws Exception {
//        given(communityPostService.getPostById(1)).willThrow(new DataNotFoundException("Post with ID: 1 not found"));
//
//        mockMvc.perform(get("/posts/1"))
//                .andExpect(status().isNotFound())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.message").value("Post with ID: 1 not found"));
//    }


}
