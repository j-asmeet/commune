package com.group6.commune.Repository;

import com.group6.commune.Mapper.CommentsMapper;
import com.group6.commune.Mapper.PostMapper;
import com.group6.commune.Model.CommunityComments;
import com.group6.commune.Model.CommunityPosts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommunityCommentsImpl implements CommunityCommentsRepo{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public CommunityComments createComment(CommunityComments comments) {
        String query = """
                INSERT INTO comments(comment_id, post_id, user_id, comment, comment_date) VALUES(?,?,?,?,?)""";
        int result = jdbcTemplate.update(query,
                comments.getCommentId(),
                comments.getPostId(),
                comments.getUserId(),
                comments.getComment(),
                comments.getCommentDate()
                );

        return result ==1 ? comments : new CommunityComments();
    }

    @Override
    public int deleteComment(int id) {
        String query = """
                    DELETE FROM comments WHERE comment_id = ?;
                """;
        int res = jdbcTemplate.update(query, id);
        return res == 1 ? id : -1;
    }

    @Override
    public CommunityComments getCommentsById(int id) {
        var query = """
                    select * from comments where comment_id = ?;
                """;
        CommunityComments comments = jdbcTemplate.queryForObject(query, new Object[]{id},new CommentsMapper());
        return comments == null ? new CommunityComments() : comments;
    }
}
