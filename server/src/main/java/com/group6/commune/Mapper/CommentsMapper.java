package com.group6.commune.Mapper;

import com.group6.commune.Model.CommunityComments;
import com.group6.commune.Model.CommunityPosts;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentsMapper implements RowMapper<CommunityComments> {

    @Override
    public CommunityComments mapRow(ResultSet rs, int rowNum) throws SQLException {
        CommunityComments comments = new CommunityComments();
        comments.setPostId(rs.getInt("post_id"));
        comments.setComment(rs.getString("comment"));
        comments.setCommentDate(rs.getDate("comment_date"));
        comments.setCommentId(rs.getInt("comment_id"));
        comments.setUserId(rs.getInt("user_id"));
        return comments;
    }
}
