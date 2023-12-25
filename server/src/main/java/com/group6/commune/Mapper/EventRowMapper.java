package com.group6.commune.Mapper;

import org.springframework.jdbc.core.RowMapper;
import com.group6.commune.Model.Event;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventRowMapper implements RowMapper<Event> {

    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        Event event = new Event();
        event.setEventId(rs.getInt("event_id"));
        event.setEventName(rs.getString("event_name"));
        event.setShortDescription(rs.getString("short_description"));
        event.setDescription(rs.getString("event_description"));
        event.setLocation(rs.getString("location"));
        event.setEventStartTime(rs.getDate("event_start_date"));
        event.setEventEndTime(rs.getDate("event_end_date"));
        event.setEventPoster(rs.getString("event_poster"));
        event.setEntryFees(rs.getInt("entry_fees"));
        event.setEventType(rs.getString("event_type"));
        event.setCreatedByUserId(rs.getInt("created_by"));
        return event;
    }
}
