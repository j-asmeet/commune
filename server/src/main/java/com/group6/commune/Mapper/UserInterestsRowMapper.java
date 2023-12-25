package com.group6.commune.Mapper;

import com.group6.commune.Model.UserInterests;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInterestsRowMapper implements RowMapper<UserInterests>  {
    @Override
    public UserInterests mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserInterests userInterests = new UserInterests();
        userInterests.setInterest_id(rs.getInt("interest_id"));
        userInterests.setUserId(rs.getInt("user_id"));
        return userInterests;
    }
}
