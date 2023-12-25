package com.group6.commune.Mapper;

import com.group6.commune.Model.EmailDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmailDetailsMapper implements RowMapper<EmailDetails> {

    @Override
    public EmailDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

        return  new EmailDetails("", "",rs.getString("mail_body"),rs.getString("mail_subject"));

    }
}
