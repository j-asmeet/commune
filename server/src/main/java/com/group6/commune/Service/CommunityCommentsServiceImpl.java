package com.group6.commune.Service;

import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.CommunityComments;
import com.group6.commune.Model.CommunityPosts;
import com.group6.commune.Repository.CommunityCommentsImpl;
import com.group6.commune.Validators.CommentsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommunityCommentsServiceImpl implements CommunityCommentsService{


   CommunityCommentsImpl communityComments = new CommunityCommentsImpl();

    @Autowired
    @Qualifier("commentsValidator")
    private CommentsValidator commentsValidator;

    @Override
    public int deleteComment(int id) {
        CommunityComments comments = getCommentsById(id);
        if (comments == null) {
            throw new DataNotFoundException("Event does not exist");
        }
        int eventId = communityComments.deleteComment(id);
        if (eventId == -1) {
            throw new DataNotFoundException("Event does not exist");
        }
        return id;
    }

    @Override
    public CommunityComments createComment(CommunityComments comments, BindingResult result) {
        commentsValidator.validate(comments, result);
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            throw new ValidationException("Provided comment information is not valid", errors);
        }
        return communityComments.createComment(comments);
    }

    @Override
    public CommunityComments getCommentsById(int postid) {
        return communityComments.getCommentsById(postid);
    }
}
