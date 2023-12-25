package com.group6.commune.ServiceTests;

import com.group6.commune.Model.Event;
import com.group6.commune.Repository.EventRepositoryImpl;
import com.group6.commune.Service.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventRepositoryImpl eventRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
        @Test
        public void testGetAllEvents() throws ParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Event event1 = new Event(1, "Music Festival", "Live performances by popular artists", "Join us for a day full of music, food, and fun!", "Central Park", sdf.parse("2023-07-10 18:00:00"), sdf.parse("2023-07-11 02:00:00"), "music_festival_poster.jpg", 50, "Festival", 2);
            Event event2 = new Event(2, "Art Exhibition", "Discover the beauty of contemporary art", "Explore stunning artworks created by talented local artists.", "Gallery XYZ", sdf.parse("2023-07-10 18:00:00"), sdf.parse("2023-07-11 02:00:00"), "art_exhibition_poster.jpg", 10, "Exhibition", 2);
            List<Event> events = Arrays.asList(event1, event2);

            when(eventRepository.getAllEvents()).thenReturn(events);

            List<Event> result = eventService.getAllEvents();

            assertEquals(events, result);
            verify(eventRepository, times(1)).getAllEvents();
        }
    }
