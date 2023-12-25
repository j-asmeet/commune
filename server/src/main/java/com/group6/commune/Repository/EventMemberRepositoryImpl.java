package com.group6.commune.Repository;

import com.group6.commune.Mapper.EventMemberRowMapper;
import com.group6.commune.Model.EventMembers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EventMemberRepositoryImpl implements EventMemberRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<EventMembers> getAllMembers(int eventId) {
        String query = """
                    SELECT events_rsvp.event_id as event_id, events_rsvp.user_id as user_id, users.first_name, users.last_name 
                    as last_name from events_rsvp join users on events_rsvp.user_id = users.user_id where event_id =  """+ eventId+ ";";
        List<EventMembers> members = jdbcTemplate.query(query,new EventMemberRowMapper());
        return members == null? new ArrayList<>() : members;
    }

    @Override
    public EventMembers addMember(EventMembers eventMembers) {
        String query = """
                    INSERT INTO events_rsvp VALUES (?,?);
                """;

        int result = jdbcTemplate.update(query, new Object[] { eventMembers.getEventId(), eventMembers.getUserId()});
        return result ==1 ? eventMembers : new EventMembers() ;
    }

    @Override
    public EventMembers deleteMember(EventMembers eventMembers) {
        String query = """
                    DELETE FROM events_rsvp where event_id= ? and user_id = ?;
                """;
        System.out.println("event id: "+ eventMembers.getEventId());
        System.out.println("uesr id: "+ eventMembers.getUserId());
        int result = jdbcTemplate.update(query,new Object[] { eventMembers.getEventId(), eventMembers.getUserId()});
        return result == 1 ? eventMembers : new EventMembers();
    }
}
