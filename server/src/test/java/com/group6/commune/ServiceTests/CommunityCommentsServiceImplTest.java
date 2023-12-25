package com.group6.commune.ServiceTests;

import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.CommunityComments;
import com.group6.commune.Repository.CommunityCommentsRepo;
import com.group6.commune.Repository.CommunityPost;
import com.group6.commune.Service.CommunityCommentsServiceImpl;
import com.group6.commune.Validators.CommentsValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CommunityCommentsServiceImplTest {

    @Mock
    CommunityCommentsRepo communityComments;

    @Mock
    CommentsValidator commentsValidator;

    @InjectMocks
    CommunityCommentsServiceImpl communityCommentsService;

//    @Test
//    void getCommentsByIdTest() {
//        CommunityComments comments = new CommunityComments();
//        comments.setCommentId(1);
//        when(communityComments.getCommentsById(1)).thenReturn(comments);
//        assertEquals(comments, communityCommentsService.getCommentsById(1));
//    }


    @Test
    void createCommentForInvalidInputTest() {
        CommunityComments comments = new CommunityComments();
        BindingResult result = new BeanPropertyBindingResult(comments, "event");
        Map<String, String> errors = new HashMap<>();
        errors.put("comment", "Comment should not be empty or null.");
        doThrow(new ValidationException("Validation error", errors)).when(commentsValidator).validate(comments, result);

        assertThrows(ValidationException.class, () -> communityCommentsService.createComment(comments, result));
    }




}
