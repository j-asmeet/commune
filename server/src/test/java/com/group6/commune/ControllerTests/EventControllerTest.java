package com.group6.commune.ControllerTests;

import com.group6.commune.Controller.EventController;
import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.Event;
import com.group6.commune.Service.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventServiceImpl eventService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    public void getEventByIDTest() throws Exception {
        Event event = new Event();
        event.setEventId(1);
        event.setEventName("Test Event");

        given(eventService.getEventById(1)).willReturn(event);

        mockMvc.perform(get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eventId").value(1))
                .andExpect(jsonPath("$.eventName").value("Test Event"));
    }

    @Test
    public void getEventByIdWhichDoesNotExistTest() throws Exception {
        given(eventService.getEventById(1)).willThrow(new DataNotFoundException("Event with ID: 1 not found"));

        mockMvc.perform(get("/events/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Event with ID: 1 not found"));
    }

//    @Test
//    public void getAllEventForEmptyTableTest() throws Exception {
//        given(eventService.getAllEvents()).willReturn(Collections.emptyList());
//
//        mockMvc.perform(get("/events"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$").isEmpty());
//    }

//    @Test
//    public void getAllEventsTest() throws Exception {
//        Event event1 = new Event();  // assuming you have default constructor
//        event1.setEventId(1);
//        event1.setEventName("Test Event 1");
//
//        Event event2 = new Event();
//        event2.setEventId(2);
//        event2.setEventName("Test Event 2");
//
//        given(eventService.getAllEvents()).willReturn(Arrays.asList(event1, event2));
//
//        mockMvc.perform(get("/events"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$").isNotEmpty())
//                .andExpect(jsonPath("$[0].eventId").value(1))
//                .andExpect(jsonPath("$[0].eventName").value("Test Event 1"))
//                .andExpect(jsonPath("$[1].eventId").value(2))
//                .andExpect(jsonPath("$[1].eventName").value("Test Event 2"));
//    }

    @Test
    public void createEventTest() throws Exception {
        Event event = new Event();
        event.setEventId(1);
        event.setEventName("Test Event");

        given(eventService.createEvent(any(Event.class), any(BindingResult.class))).willReturn(event);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"eventId\":1,\"eventName\":\"Test Event\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eventId").value(1))
                .andExpect(jsonPath("$.eventName").value("Test Event"));
    }

    @Test
    public void createEventForInvalidDataTest() throws Exception {
        Event event = new Event();  // assuming you have default constructor
        event.setEventId(1);
        event.setEventName(""); // Assuming empty event name is invalid

        Map<String, String> errors = new HashMap<>();
        errors.put("eventName", "Event Name should not be empty or null.");

        given(eventService.createEvent(any(Event.class), any(BindingResult.class)))
                .willThrow(new ValidationException("Validation failed", errors));

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"eventId\":1,\"eventName\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.eventName").value("Event Name should not be empty or null."));
    }



    @Test
    public void updateEventTest() throws Exception {
        Event event = new Event();
        event.setEventId(1);
        event.setEventName("Updated Test Event");

        given(eventService.updateEvent(any(Event.class), any(BindingResult.class))).willReturn(event);

        mockMvc.perform(put("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"eventId\":1,\"eventName\":\"Updated Test Event\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eventId").value(1))
                .andExpect(jsonPath("$.eventName").value("Updated Test Event"));
    }

    @Test
    public void updateEventForRecordDoesNotExistTest() throws Exception {
        given(eventService.updateEvent(any(Event.class), any(BindingResult.class)))
                .willThrow(new DataNotFoundException("Event with ID: 1 not found"));

        mockMvc.perform(put("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"eventId\":1,\"eventName\":\"Updated Test Event\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Event with ID: 1 not found"));
    }

//    @Test
//    public void deleteEventTest() throws Exception {
//        Event event = new Event();
//        event.setEventId(1);
//        event.setEventName("Test Event");
//
//        given(eventService.deleteEvent(1)).willReturn(1);
//
//        mockMvc.perform(delete("/events/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.eventId").value(1))
//                .andExpect(jsonPath("$.eventName").value("Test Event"));
//    }

    @Test
    public void deleteEventForRecordDoesNotExistTest() throws Exception {
        given(eventService.deleteEvent(1)).willThrow(new DataNotFoundException("Event with ID: 1 not found"));

        mockMvc.perform(delete("/events/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Event with ID: 1 not found"));
    }

}

