package com.group6.commune.Model;

public class CommunityPosts {

    private int postId;

    private int userId;

    private int communityId;

    private String postTitle;

    private String description;
    private String postImage;

    public CommunityPosts(int postId, int userId, int communityId, String postTitle, String description, String postImage) {
        this.postId = postId;
        this.userId = userId;
        this.communityId = communityId;
        this.postTitle = postTitle;
        this.description = description;
        this.postImage = postImage;
    }

    public CommunityPosts() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }


}

