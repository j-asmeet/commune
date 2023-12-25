package com.group6.commune.ControllerTests;

import com.group6.commune.Controller.CommentsController;
import com.group6.commune.Controller.PostController;
import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.CommunityComments;
import com.group6.commune.Repository.CommunityCommentsImpl;
import com.group6.commune.Service.CommunityCommentsServiceImpl;
import com.group6.commune.Service.CommunityPostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CommentsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CommunityCommentsServiceImpl commentsService;

    @InjectMocks
    private CommentsController commentsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentsController).build();
    }

    @Test
    public void getCommentByIDTest() throws Exception {
        CommunityComments comments = new CommunityComments();
        comments.setCommentId(1);
        comments.setComment("Test Comment");
        given(commentsService.getCommentsById(1)).willReturn(comments);

        mockMvc.perform(get("/comments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.commentId").value(1))
                .andExpect(jsonPath("$.comment").value("Test Comment"));
    }

    @Test
    public void getCommentByIdWhichDoesNotExistTest() throws Exception {
        int commentId = 123;

        // Mock the behavior of getCommentsById to return null, indicating that the comment does not exist
        when(commentsService.getCommentsById(commentId)).thenReturn(null);

        // Call the method
        ResponseEntity<CommunityComments> response = commentsController.getCommentById(commentId);


        // Verify the response
        assertEquals(200, response.getStatusCodeValue()); // 404 - Not Found
        assertNull(response.getBody());

        // Verify that getCommentsById was called once with the correct parameter
        verify(commentsService, times(1)).getCommentsById(commentId);
    }

    @Test
    public void createCommentTest() throws Exception {
        CommunityComments comments = new CommunityComments();
        comments.setCommentId(1);
        comments.setComment("Test Comment");

        given(commentsService.createComment(any(CommunityComments.class), any(BindingResult.class))).willReturn(comments);

        mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"commentId\":1,\"comment\":\"Test Comment\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.commentId").value(1))
                .andExpect(jsonPath("$.comment").value("Test Comment"));
    }

}
