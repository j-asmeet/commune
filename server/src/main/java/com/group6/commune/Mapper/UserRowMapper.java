package com.group6.commune.Mapper;

import com.group6.commune.Model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setDob(rs.getDate("dob"));
        user.setGender(rs.getString("gender"));
        user.setEmail(rs.getString("email"));
        user.setContact(rs.getString("contact"));
        user.setPassword(rs.getString("password"));
        user.setProfilePic(rs.getString("profile_pic"));
        user.setEnrollmentDate(rs.getDate("enrollment_date"));
        return user;
    }
}
