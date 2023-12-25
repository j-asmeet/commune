package com.group6.commune.ServiceTests;

import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.Event;
import com.group6.commune.Repository.EventRepository;
import com.group6.commune.Service.EventServiceImpl;
import com.group6.commune.Validators.EventValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    EventRepository eventRepository;

    @Mock
    EventValidator eventValidator;

    @InjectMocks
    EventServiceImpl eventService;

    @Test
    void getAllEventsTest() {
        when(eventRepository.getAllEvents()).thenReturn(Arrays.asList(new Event(), new Event()));

        assertEquals(2, eventService.getAllEvents().size());
    }

    @Test
    void getAllEventsForEmptyDatabaseTest() {
        when(eventRepository.getAllEvents()).thenReturn(Collections.emptyList());

        assertThrows(DataNotFoundException.class, () -> eventService.getAllEvents());
    }

    @Test
    void createEventTest() {
        Event event = new Event();
        BindingResult result = new BeanPropertyBindingResult(event, "event");

        when(eventRepository.createEvent(event)).thenReturn(event);

        assertEquals(event, eventService.createEvent(event, result));
    }

    @Test
    void createEventForInvalidParametersTest() {
        Event event = new Event();
        BindingResult result = new BeanPropertyBindingResult(event, "event");
        result.rejectValue("eventName", "", "Event Name should not be empty or null.");

        assertThrows(ValidationException.class, () -> eventService.createEvent(event, result));
    }

    @Test
    void getEventByIdTest() {
        Event event = new Event();
        event.setEventId(1);

        when(eventRepository.getEventById(1)).thenReturn(event);

        assertEquals(event, eventService.getEventById(1));
    }

    @Test
    void getEventByIdForRecordDoesNotExistTest() {
        when(eventRepository.getEventById(1)).thenThrow(new DataNotFoundException("Event with ID: 1 not found"));

        assertThrows(DataNotFoundException.class, () -> eventService.getEventById(1));
    }

    @Test
    void updateEventTest() {
        Event event = new Event();
        event.setEventId(1);
        BindingResult result = new BeanPropertyBindingResult(event, "event");

        when(eventRepository.updateEvent(event)).thenReturn(event);

        assertEquals(event, eventService.updateEvent(event, result));
    }

    @Test
    void updateEventForInvalidDataTest() {
        Event event = new Event();
        event.setEventId(1);
        BindingResult result = new BeanPropertyBindingResult(event, "event");
        result.rejectValue("eventName", "", "Event Name should not be empty or null.");

        assertThrows(ValidationException.class, () -> eventService.updateEvent(event, result));
    }

//    @Test
//    void deleteEventTest() {
//        Event event = new Event();
//        event.setEventId(1);
//
//        when(eventRepository.getEventById(1)).thenReturn(event);
//        when(eventRepository.deleteEvent(1)).thenReturn(1);
//
//        assertEquals(event, eventService.deleteEvent(1));
//    }

    @Test
    void deleteEventForRecordDoesNotExists() {
        when(eventRepository.getEventById(1)).thenReturn(null);

        assertThrows(DataNotFoundException.class, () -> eventService.deleteEvent(1));
    }

    @Test
    void getEventByIdForZeroRowsFetchedTest() {
        when(eventRepository.getEventById(1)).thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataNotFoundException.class, () -> eventService.getEventById(1));
    }

    @Test
    void createEventForInvalidInputTest() {
        Event event = new Event();
        BindingResult result = new BeanPropertyBindingResult(event, "event");
        Map<String, String> errors = new HashMap<>();
        errors.put("eventName", "Event Name should not be empty or null.");
        doThrow(new ValidationException("Validation error", errors)).when(eventValidator).validate(event, result);

        assertThrows(ValidationException.class, () -> eventService.createEvent(event, result));
    }

    @Test
    void updateEventValidationExceptionTest() {
        Event event = new Event();
        BindingResult result = new BeanPropertyBindingResult(event, "event");
        Map<String, String> errors = new HashMap<>();
        errors.put("eventName", "Event Name should not be empty or null.");
        doThrow(new ValidationException("Validation error", errors)).when(eventValidator).validate(event, result);

        assertThrows(ValidationException.class, () -> eventService.updateEvent(event, result));
    }

    @Test
    void deleteEventEventNotFoundExceptionTest() {
        when(eventRepository.getEventById(1)).thenReturn(null);

        assertThrows(DataNotFoundException.class, () -> eventService.deleteEvent(1));
    }

}
