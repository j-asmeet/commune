package com.group6.commune.Service;

import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.CommunityPosts;
import com.group6.commune.Model.Event;
import com.group6.commune.Repository.CommunityPostsImpl;
import com.group6.commune.Validators.EventValidator;
import com.group6.commune.Validators.PostsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommunityPostServiceImpl implements CommunityPostsService{


    @Autowired
    CommunityPostsImpl communityPosts;

    @Autowired
    @Qualifier("postsValidator")
    private PostsValidator postsValidator;

    @Override
    public List<CommunityPosts> getAllPosts() {
        List<CommunityPosts> posts = communityPosts.getAllPosts();
        System.out.println("posts: "+ posts);
        return posts.size() > 0 ? posts : new ArrayList<>();
    }

    @Override
    public CommunityPosts createPosts(CommunityPosts posts, BindingResult result) {
        postsValidator.validate(posts, result);
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            throw new ValidationException("Provided event information is not valid", errors);
        }
        return communityPosts.createPosts(posts);
    }
    @Override
    public List<CommunityPosts> getPostByCommunityId(int id) {
        List<CommunityPosts> posts = communityPosts.getPostsByCommunity(id);
        System.out.println("posts: "+ posts);
        return posts.size() > 0 ? posts : new ArrayList<>();
    }


    @Override
    public CommunityPosts getPostById(int id) {
        return communityPosts.getPostById(id);
    }

    @Override
    public CommunityPosts updatePost(CommunityPosts posts, BindingResult result) {
        if (posts == null) {
            throw new DataNotFoundException("Post does not exist");
        }
        postsValidator.validate(posts, result);
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            throw new ValidationException("Validation error", errors);
        }
        CommunityPosts updatedPosts = communityPosts.updatePosts(posts);
        if (updatedPosts == null) {
            throw new DataNotFoundException("Post does not exist");
        }
        return updatedPosts;
    }

    @Override
    public int deletePosts(int id) {
        CommunityPosts posts = getPostById(id);
        if (posts == null) {
            throw new DataNotFoundException("Event does not exist");
        }
        int postId = communityPosts.deletePosts(id);

        if (postId == -1) {
            throw new DataNotFoundException("Event does not exist");
        }
        return id;
    }
}
