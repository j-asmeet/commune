/**
 * This class represents the controller for managing events.
 * Author: Kruti Panchal
 */
package com.group6.commune.Controller;

import com.group6.commune.AppLogger.AppLogger;
import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.UnauthorizedAccessException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.Event;
import com.group6.commune.Model.Interest;
import com.group6.commune.Service.EventServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventServiceImpl eventService;

    Logger logger = AppLogger.getLogger();
    /**
     * Retrieves all events or events matching a specific event title.
     *
     * @param eventTitle The optional event title to filter events.
     * @return A ResponseEntity containing a list of Event objects and HTTP status 200 if successful.
     * @throws DataNotFoundException If no events are found with the given event title.
     * @author Kruti Panchal
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(@RequestParam(required = false, name = "event_title") String eventTitle) {
        if (eventTitle == null || eventTitle.isEmpty() || eventTitle.isBlank()) {
            logger.info("Get all events in the /events/");
            return ResponseEntity.ok(eventService.getAllEvents());
        } else {
            logger.info("Get Events in the /events/"+ eventTitle);
            return ResponseEntity.ok(eventService.getEventByName(eventTitle));
        }
    }

    /**
     * Creates a new event.
     *
     * @param event  The Event object containing the event data.
     * @param result The BindingResult object for validation.
     * @return A ResponseEntity containing the created Event object and HTTP status 201 if successful.
     * @throws ValidationException If the input data fails validation.
     * @author Kruti Panchal
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event, BindingResult result) {
        logger.info("create event for /events/"+ event.getEventId());
        return new ResponseEntity<>(eventService.createEvent(event, result), HttpStatus.CREATED);
    }

    /**
     * Retrieves an event by its ID.
     *
     * @param id The ID of the event to be retrieved.
     * @return A ResponseEntity containing the retrieved Event object and HTTP status 200 if successful.
     * @throws DataNotFoundException If the event with the given ID is not found.
     * @author Kruti Panchal
     *
     */

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable int id){
        logger.info("Get event for /events/"+ id);
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    /**
     * Retrieves all events created by a specific user.
     *
     * @param userId The ID of the user whose created events are to be retrieved.
     * @return A ResponseEntity containing a list of Event objects and HTTP status 200 if successful.
     * @throws DataNotFoundException If the user has not created any events.
     * @throws UnauthorizedAccessException If the user is not authorized to access events created by the given user.
     * @author Kruti Panchal
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Event>> getUserCreatedEvents(@PathVariable int userId) {
        logger.info("Get event for communities /events/user/"+ userId);
        return ResponseEntity.ok(eventService.getUserCreatedEvents(userId));
    }

    /**
     * Updates an existing event.
     *
     * @param event  The Event object containing the updated event data.
     * @param result The BindingResult object for validation.
     * @return A ResponseEntity containing the updated Event object and HTTP status 200 if successful.
     * @throws DataNotFoundException If the event with the given ID is not found.
     * @throws UnauthorizedAccessException If the user is not authorized to update the event.
     * @throws ValidationException If the input data fails validation.
     * @autho Kruti Panchal
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping
    public ResponseEntity<Event> updateEvent(@RequestBody Event event, BindingResult result){
        logger.info("Update event /events/user/"+ event.getEventId());
        return ResponseEntity.ok(eventService.updateEvent(event, result));
    }

    /**
     * Deletes an event by its ID.
     *
     * @param id The ID of the event to be deleted.
     * @return A ResponseEntity containing the number of events deleted and HTTP status 200 if successful.
     * @throws DataNotFoundException If the event with the given ID is not found.
     * @throws UnauthorizedAccessException If the user is not authorized to delete the event.
     * @autho Kruti Panchal
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteEvent(@PathVariable int id){
        logger.info("Delete event for /events/"+ id);
        return ResponseEntity.ok(eventService.deleteEvent(id));
    }

    /**
     * Adds an interest to an event.
     *
     * @param id          The ID of the event to which the interest will be added.
     * @param interest_id The ID of the interest to be added.
     * @return A ResponseEntity containing true if the addition is successful and HTTP status 200 if successful.
     * @throws DataNotFoundException If the event or interest with the given ID is not found.
     * @throws UnauthorizedAccessException If the user is not authorized to add interests to the event.
     * @throws ValidationException If the input data fails validation.
     * @autho Kruti Panchal
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/{id}/interests")
    public ResponseEntity<Boolean> AddEventInterest(@PathVariable int id, @RequestParam(required = true, name= "interest_id") int interest_id) {
        logger.info("Add event Interest: /events/"+ id+"/interests");
        return new ResponseEntity<>(eventService.addEventInterests(id,interest_id), HttpStatus.CREATED);
    }

    /**
     * Retrieves all interests associated with an event by its ID.
     *
     * @param id The ID of the event whose interests are to be retrieved.
     * @return A ResponseEntity containing a list of Interest objects and HTTP status 200 if successful.
     * @throws DataNotFoundException If the event with the given ID is not found.
     * @throws UnauthorizedAccessException If the user is not authorized to access the interests of the event.
     * @throws ValidationException If the input data fails validation.
     * @autho Kruti Panchal
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}/interests")
    public ResponseEntity<List<Interest>> getEventInterests(@PathVariable int id) {
        logger.info("get event Interests: /events/"+ id+"/interests");
        return ResponseEntity.ok(eventService.getEventInterests(id));
    }

    /**
     * Deletes an interest from an event.
     *
     * @param id          The ID of the event from which the interest will be deleted.
     * @param interest_id The ID of the interest to be deleted.
     * @return A ResponseEntity containing true if the deletion is successful and HTTP status 200 if successful.
     * @throws DataNotFoundException If the event or interest with the given ID is not found.
     * @throws UnauthorizedAccessException If the user is not authorized to delete interests from the event.
     * @throws ValidationException If the input data fails validation.
     * @autho Kruti Panchal
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("{id}/interests")
    public ResponseEntity<Boolean> deleteEventInterest(@PathVariable int id, @RequestParam( required = false, name ="interest_id") int interest_id){
        logger.info("delete event Interests: /events/"+ id+"/interests");
        return ResponseEntity.ok(eventService.deleteEventInterests(id,interest_id));
    }

    /**
     * Retrieves all events associated with a community by its ID.
     *
     * @param id The ID of the community whose events are to be retrieved.
     * @return A ResponseEntity containing a list of Event objects and HTTP status 200 if successful.
     * @throws DataNotFoundException If the community with the given ID is not found.
     * @throws UnauthorizedAccessException If the user is not authorized to access the events of the community.
     * @throws ValidationException If the input data fails validation.
     * @autho Kruti Panchal
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}/events")
    public ResponseEntity<List<Event>> getEventsForCommunity(@PathVariable int id){
        logger.info("get events for community: /events/"+ id+"/events");
        return ResponseEntity.ok(eventService.getEventsForCommunity(id));
    }

    /**
     * Exception handler for DataNotFoundException.
     *
     * @param ex The DataNotFoundException object to be handled.
     * @return A ResponseEntity containing a message about the exception and HTTP status 404.
     * @author Harsh Patel
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDataNotFoundException(DataNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", ex.getMessage()));
    }

    /**
     * Exception handler for ValidationException.
     *
     * @param ex The ValidationException object to be handled.
     * @return A ResponseEntity containing a message and validation errors about the exception and HTTP status 400.
     * @author Kruti Panchal
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage(), "errors", ex.getErrors()));
    }

    /**
     * Exception handler for UnauthorizedAccessException.
     *
     * @param ex The UnauthorizedAccessException object to be handled.
     * @return A ResponseEntity containing a message about the exception and HTTP status 401.
     * @author Jasmeet Singh
     */
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", ex.getMessage()));
    }
}
