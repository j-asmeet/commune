package com.group6.commune.Repository;

import com.group6.commune.Mapper.PostMapper;
import com.group6.commune.Model.CommunityPosts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Component
public class CommunityPostsImpl implements CommunityPost {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public CommunityPosts createPosts(CommunityPosts posts) {

        String query = """
                INSERT INTO posts (post_id, user_id, community_id, title, description, post_image) VALUES(?,?,?,?,?,?)""";
        int result = jdbcTemplate.update(query,
                        posts.getPostId(),
                        posts.getUserId(),
        posts.getCommunityId(),
        posts.getPostTitle(),
        posts.getDescription(),
        posts.getPostImage());
        
        return result ==1 ? posts : new CommunityPosts();
    }

    @Override
    public List<CommunityPosts> getPostsByCommunity(int communityId) {
        var query = " select * from posts where community_id = ?; ";
        List<CommunityPosts> posts = jdbcTemplate.query(query, new Object[]{communityId},new PostMapper());
        return posts;

    }


    @Override
    public CommunityPosts updatePosts(CommunityPosts posts) {

        String query= """
                  UPDATE posts
                  SET
                      title = ?,
                      description = ?,
                      post_image = ?
                  WHERE
                      post_id = ?;
                """;

        int res = jdbcTemplate.update(query,
                posts.getPostTitle(),
                posts.getDescription(),
                posts.getPostImage(),
                posts.getPostId());

        return res == 1 ? posts : new CommunityPosts();

    }

    @Override
    public int deletePosts(int id) {
        String query = """
                    DELETE FROM posts WHERE post_id = ?;
                """;
        int res = jdbcTemplate.update(query, id);
        return res == 1 ? id : -1;
    }

    @Override
    public CommunityPosts getPostById(int id) {
        String query = "SELECT * FROM posts where post_id=?";
        CommunityPosts event = jdbcTemplate.queryForObject(query, new Object[]{id},new PostMapper());
        return event == null ? new CommunityPosts() : event;
    }

    @Override
    public List<CommunityPosts> getAllPosts() {
        var query = """
                    select * from posts;
                """;
        return jdbcTemplate.query(query,new PostMapper());
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
