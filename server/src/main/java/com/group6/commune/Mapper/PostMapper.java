package com.group6.commune.Mapper;

import com.group6.commune.Model.CommunityPosts;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostMapper implements RowMapper<CommunityPosts> {

    @Override
    public CommunityPosts mapRow(ResultSet rs, int rowNum) throws SQLException {
        CommunityPosts posts = new CommunityPosts();
        posts.setPostId(rs.getInt("post_id"));
        posts.setPostTitle(rs.getString("title"));
        posts.setDescription(rs.getString("description"));
        posts.setPostImage(rs.getString("post_image"));
        posts.setUserId(rs.getInt("user_id"));
        posts.setCommunityId(rs.getInt("community_id"));
        return posts;
    }
}
