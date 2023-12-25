package com.group6.commune.Mapper;

import com.group6.commune.Model.Interest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InterestRowMapper implements RowMapper<Interest> {

    @Override
    public Interest mapRow(ResultSet rs, int rowNum) throws SQLException {
        Interest interest = new Interest();
        interest.setInterestId(rs.getInt("interest_id"));
        interest.setInterestName(rs.getString("name"));
        interest.setInterestCategory(rs.getString("category"));
        return interest;
    }
}
