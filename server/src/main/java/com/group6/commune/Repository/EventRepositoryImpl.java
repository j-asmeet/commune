package com.group6.commune.Repository;

import com.group6.commune.Mapper.EventRowMapper;
import com.group6.commune.Mapper.InterestRowMapper;
import com.group6.commune.Model.Event;
import com.group6.commune.Model.Interest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.group6.commune.Utils.IDgenerator;


import java.util.ArrayList;
import java.util.List;

@Repository
public class EventRepositoryImpl implements EventRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    public List<Event> getAllEvents(){
        var query = """
                    select * from events;
                """;
        return jdbcTemplate.query(query,new EventRowMapper());
    }

    public Event createEvent(Event event){
        int id = IDgenerator.generateId();
        event.setEventId(id);
        String query = """
                INSERT INTO events (event_id,event_name, short_description,
                event_description, location, event_start_date, event_end_date,
                event_poster, entry_fees, event_type, created_by) VALUES(?,?,?,?,?,?,?,?,?,?,?);
                """;

        int result = jdbcTemplate.update(query,
                event.getEventId(),
                event.getEventName(),
                event.getShortDescription(),
                event.getDescription(),
                event.getLocation(),
                event.getEventStartTime(),
                event.getEventEndTime(),
                event.getEventPoster(),
                event.getEntryFees(),
                event.getEventType(),
                event.getCreatedByUserId());

        return result ==1 ? event : new Event();
    }

    @Override
    public Event getEventById(int eventId) {
        String query = "SELECT * FROM events where event_id=?";
        Event event = jdbcTemplate.queryForObject(query, new Object[]{eventId},new EventRowMapper());
        return event == null ? new Event() : event;
    }

    public Event updateEvent(Event event){
        String query= """
                  UPDATE events
                  SET
                      event_name = ?,
                      short_description = ?,
                      event_description = ?,
                      location = ?,
                      event_start_date = ?,
                      event_end_date = ?,
                      event_poster = ?,
                      entry_fees = ?,
                      event_type = ?,
                      created_by = ?
                  WHERE
                      event_id = ?;
                """;

        int res = jdbcTemplate.update(query,
                event.getEventName(),
                event.getShortDescription(),
                event.getDescription(),
                event.getLocation(),
                event.getEventStartTime(),
                event.getEventEndTime(),
                event.getEventPoster(),
                event.getEntryFees(),
                event.getEventType(),
                event.getCreatedByUserId(),
                event.getEventId());

        return res == 1 ? event : new Event();
    }

    @Override
    public int deleteEvent(int id) {
        String query = """
                    DELETE FROM events WHERE event_id = ?;
                """;
        int res = jdbcTemplate.update(query, id);
        return res == 1 ? id : -1;
    }

    @Override
    public Boolean addEventInterests(int id, int interest_id) {
        String query = "INSERT INTO events_interests (event_id, interest_id) VALUES(?,?);";
        int result = jdbcTemplate.update(query, new Object[]{id, interest_id});
        return result ==1;
    }

    @Override
    public List<Interest> getEventInterestsById(int eventId) {
        String query = "SELECT interests.interest_id as interest_id, interests.name as name, interests.category as category FROM interests, events_interests " +
                "WHERE interests.interest_id = events_interests.interest_id AND events_interests.event_id = " + eventId;
        return jdbcTemplate.query(query, new InterestRowMapper());
    }

    @Override
    public Boolean deleteEventInterests(int eventId, int interestId){
        String query = "DELETE FROM events_interests WHERE event_id=? AND interest_id = ?";

        int res = jdbcTemplate.update(query, new Object[]{eventId, interestId});

        if(res == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<Event> getEventByName(String eventName) {
        String query = "SELECT * FROM events where event_name LIKE \"" + "%"+eventName +"%\"";
        List<Event> events = jdbcTemplate.query(query,new EventRowMapper());
        return events == null ? new ArrayList<>() : events;
    }

    @Override
    public List<Event> getUserCreatedEvents(int userId) {
        String query = "SELECT * FROM events where created_by=?";
        List<Event> events = jdbcTemplate.query(query, new Object[]{userId},new EventRowMapper());
        return events == null ? new ArrayList<>() : events;
    }


    @Override
    public List<Event> getEventsForCommunity(int communityId){
        String query = """
                      SELECT e.* FROM community_interest ci JOIN events_interests ei ON ci.interest_id = ei.interest_id JOIN events e ON ei.event_id = e.event_id WHERE ci.community_id = 1339328373;
                      """;
        List<Event> events = jdbcTemplate.query(query,new EventRowMapper());
        return events == null ? new ArrayList<>() : events;
    }
}


