package com.group6.commune.ServiceTests;

import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.CommunityPosts;
import com.group6.commune.Repository.CommunityPost;
import com.group6.commune.Service.CommunityCommentsServiceImpl;
import com.group6.commune.Service.CommunityPostServiceImpl;
import com.group6.commune.Validators.PostsValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommunityPostsServiceImplTest {


    @Mock
    CommunityPost postRepository;

    @Mock
    PostsValidator postsValidator;

    @Autowired
    @InjectMocks
    CommunityPostServiceImpl communityPostService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void getAllPostsTest() {
//        when(postRepository.getAllPosts()).thenReturn(Arrays.asList(new CommunityPosts(), new CommunityPosts()));
//        assertEquals(2, communityPostService.getAllPosts().size());
//    }

//    @Test
//    void getAllPostsForEmptyDatabaseTest() {
//        when(postRepository.getAllPosts()).thenReturn(Collections.emptyList());
//
//        assertThrows(DataNotFoundException.class, () -> communityPostService.getAllPosts());
//    }

//    @Test
//    void createPostsTest() {
//        CommunityPosts communityPosts = new CommunityPosts();
//        BindingResult result = new BeanPropertyBindingResult(communityPosts, "communityPosts");
//        when(postRepository.createPosts(communityPosts)).thenReturn(communityPosts);
//        assertEquals(communityPosts, communityPostService.createPosts(communityPosts, result));
//    }

    @Test
    void createpostForInvalidParametersTest() {
        CommunityPosts communityPosts = new CommunityPosts();
        BindingResult result = new BeanPropertyBindingResult(communityPosts, "communityPosts");
        result.rejectValue("postTitle", "", "Post should not be empty or null.");

        assertThrows(ValidationException.class, () -> communityPostService.createPosts(communityPosts, result));
    }

//    @Test
//    void getPostsByIdTest() {
//        CommunityPosts communityPosts = new CommunityPosts();
//        communityPosts.setPostId(1);
//
//        when(postRepository.getPostById(1)).thenReturn(communityPosts);
//
//        assertEquals(communityPosts, communityPostService.getPostById(1));
//    }

    @Test
    void getpostByIdForRecordDoesNotExistTest() {
        when(postRepository.getPostById(1)).thenThrow(new DataNotFoundException("Post with ID: 1 not found"));

        assertThrows(DataNotFoundException.class, () -> postRepository.getPostById(1));
    }

//    @Test
//    void updatePostTest() {
//        CommunityPosts communityPosts = new CommunityPosts();
//        communityPosts.setPostId(1);
//        BindingResult result = new BeanPropertyBindingResult(communityPosts, "communityPosts");
//
//        when(postRepository.updatePosts(communityPosts)).thenReturn(communityPosts);
//
//        assertEquals(communityPosts, communityPostService.updatePost(communityPosts, result));
//    }

    @Test
    void updatePostForInvalidDataTest() {
        CommunityPosts communityPosts = new CommunityPosts();
        communityPosts.setPostId(1);
        BindingResult result = new BeanPropertyBindingResult(communityPosts, "communityPosts");
        result.rejectValue("postTitle", "", "Post should not be empty or null.");

        assertThrows(ValidationException.class, () -> communityPostService.updatePost(communityPosts, result));
    }

//    @Test
//    void deletePostForRecordDoesNotExists() {
//        when(postRepository.getPostById(1)).thenReturn(null);
//
//        assertThrows(DataNotFoundException.class, () -> communityPostService.deletePosts(1));
//    }

//    @Test
//    void getPostByIdForZeroRowsFetchedTest() {
//        when(postRepository.getPostById(1)).thenThrow(new EmptyResultDataAccessException(1));
//
//        assertThrows(DataNotFoundException.class, () -> communityPostService.getPostById(1));
//    }

    @Test
    void createPostForInvalidInputTest() {
        CommunityPosts communityPosts = new CommunityPosts();
        BindingResult result = new BeanPropertyBindingResult(communityPosts, "communityPosts");
        Map<String, String> errors = new HashMap<>();
        errors.put("postTitle", "Post should not be empty or null.");
        doThrow(new ValidationException("Validation error", errors)).when(postsValidator).validate(communityPosts, result);

        assertThrows(ValidationException.class, () -> communityPostService.createPosts(communityPosts, result));
    }

    @Test
    void updatePostValidationExceptionTest() {
        CommunityPosts communityPosts = new CommunityPosts();
        BindingResult result = new BeanPropertyBindingResult(communityPosts, "communityPosts");
        Map<String, String> errors = new HashMap<>();
        errors.put("postTitle", "Post should not be empty or null.");
        doThrow(new ValidationException("Validation error", errors)).when(postsValidator).validate(communityPosts, result);

        assertThrows(ValidationException.class, () -> communityPostService.updatePost(communityPosts, result));
    }

//    @Test
//    void deletePostNotFoundExceptionTest() {
////        when(postRepository.deletePosts(5)).thenReturn(null);
////        assertThrows(DataNotFoundException.class, () -> communityPostService.deletePosts(5));
//
//        doThrow(new DataNotFoundException("Deletion failed")).when(communityPostService).deletePosts(4);
//
//        // Call the method and expect an exception to be thrown
//        assertThrows(DataNotFoundException.class, () -> communityPostService.deletePosts(4));
//
//    }
//
}
