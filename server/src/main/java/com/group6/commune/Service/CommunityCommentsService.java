package com.group6.commune.Service;

import com.group6.commune.Model.CommunityComments;
import com.group6.commune.Model.CommunityPosts;
import org.springframework.validation.BindingResult;

public interface CommunityCommentsService {
    int deleteComment(int id);
    CommunityComments createComment(CommunityComments comments, BindingResult result);

    CommunityComments getCommentsById(int id);
}
