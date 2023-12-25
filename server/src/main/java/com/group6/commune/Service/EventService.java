package com.group6.commune.Service;

import com.group6.commune.Model.Event;
import com.group6.commune.Model.Interest;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface EventService {
    List<Event> getAllEvents();
    Event createEvent(Event event, BindingResult result);
    Event getEventById(int id);
    Event updateEvent(Event event, BindingResult result );
    int deleteEvent(int id);
    Boolean addEventInterests(int id, int interest_id);
    List<Interest> getEventInterests(int id);
    Boolean deleteEventInterests(int id, int interest_id);
    List<Event> getEventByName(String eventTitle);
    List<Event> getUserCreatedEvents(int userId);

    List<Event> getEventsForCommunity(int id);
}
