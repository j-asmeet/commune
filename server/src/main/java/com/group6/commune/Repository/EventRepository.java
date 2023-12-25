package com.group6.commune.Repository;

import com.group6.commune.Model.Event;
import com.group6.commune.Model.Interest;

import java.util.List;

public interface EventRepository {
    List<Event> getAllEvents();
    Event createEvent(Event event);
    Event getEventById(int id);
    Event updateEvent(Event event);
    int deleteEvent(int id);

    Boolean addEventInterests(int id, int interest_id);
    List<Interest> getEventInterestsById(int id);
    Boolean deleteEventInterests(int event_id, int interest_id);
    List<Event> getEventByName(String eventTitle);
    List<Event> getUserCreatedEvents(int userId);

    List<Event> getEventsForCommunity(int communityID);
}
