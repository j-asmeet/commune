package com.group6.commune.Model;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CommunityComments {

    private int commentId;

    private int postId;
    private int userId;
    private String comment;
    private Date commentDate;

    public CommunityComments()
    {

    }


    public CommunityComments(int commentId, int postId, int userId, String comment, Date commentDate) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.comment = comment;
        this.commentDate = commentDate;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }
}
