package com.group6.commune.Mapper;

import com.group6.commune.Model.Community;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommunityRowMapper implements RowMapper<Community> {

    @Override
    public Community mapRow(ResultSet rs, int rowNum) throws SQLException {
        Community community = new Community();

        community.setCommunity_id(rs.getInt("community_id"));
        community.setCreated_by(rs.getInt("created_by"));
        community.setName(rs.getString("name"));
        community.setDescription(rs.getString("description"));
        community.setDisplay_image(rs.getString("display_image"));
        return community;
    }
}
