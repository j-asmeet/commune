package com.group6.commune.Repository;

import com.group6.commune.Mapper.EmailDetailsMapper;
import com.group6.commune.Model.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class EmailTemplateRepositoryImpl implements EmailTemplateRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public EmailDetails getEmailDetailsFromDB(int id)
    {
        String query = "SELECT * FROM emails_templates WHERE id=?";

        return jdbcTemplate.queryForObject(query, new Object[]{id}, new EmailDetailsMapper());

    }

}
