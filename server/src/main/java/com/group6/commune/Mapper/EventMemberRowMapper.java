package com.group6.commune.Mapper;

import com.group6.commune.Model.EventMembers;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventMemberRowMapper implements RowMapper<EventMembers> {

    @Override
    public EventMembers mapRow(ResultSet rs, int rowNum) throws SQLException {
        EventMembers eventMembers = new EventMembers();
        eventMembers.setEventId(rs.getInt("event_id"));
        eventMembers.setUserId(rs.getInt("user_id"));
        return eventMembers;
    }
}
