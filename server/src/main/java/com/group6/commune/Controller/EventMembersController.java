/**
 * This class represents the controller for managing event members.
 * Author: Kruti Panchal
 */
package com.group6.commune.Controller;

import com.group6.commune.AppLogger.AppLogger;
import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.UnauthorizedAccessException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.EventMembers;
import com.group6.commune.Service.EventMemberServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events/{eventId}/users")
public class EventMembersController {

    @Autowired
    EventMemberServiceImpl eventMemberService;

    Logger logger = AppLogger.getLogger();

    /**
     * Adds a new member to the event.
     *
     * @param eventMember The EventMembers object containing the member data.
     * @param result      The BindingResult object for validation.
     * @return A ResponseEntity containing the created EventMembers object and HTTP status 200 if successful.
     * @throws ValidationException If the input data fails validation.
     * @author Kruti Panchal
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<EventMembers> addMember(@RequestBody EventMembers eventMember, BindingResult result) {
        logger.info("Add member for event: /events/{eventId}/users"+ eventMember.getUserId());
        return ResponseEntity.ok(eventMemberService.addMember(eventMember, result));
    }

    /**
     * Retrieves all members of the event by event ID.
     *
     * @param eventId The ID of the event whose members are to be retrieved.
     * @return A ResponseEntity containing a list of EventMembers objects and HTTP status 200 if successful.
     * @throws DataNotFoundException If the event with the given ID is not found.
     * @author Kruti Panchal
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<EventMembers>> getAllMembers(@PathVariable int eventId) {
        logger.info("get members for event: /events/{eventId}/users/"+ eventId);
        return new ResponseEntity<>(eventMemberService.getAllMembers(eventId), HttpStatus.OK);
    }

    /**
     * Deletes a member from the event.
     *
     * @param eventMember The EventMembers object containing the member data.
     * @return A ResponseEntity containing the deleted EventMembers object and HTTP status 200 if successful.
     * @throws DataNotFoundException If the member with the given data is not found in the event.
     * @throws UnauthorizedAccessException If the user is not authorized to delete the member from the event.
     * @autho Kruti Panchal
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping
    public ResponseEntity<EventMembers> deleteMember(@RequestBody EventMembers eventMember) {
        logger.info("delete member for event: /events/{eventId}/users"+ eventMember.getUserId());
        return ResponseEntity.ok(eventMemberService.deleteMember(eventMember));
    }

    /**
     * Exception handler for DataNotFoundException.
     *
     * @param ex The DataNotFoundException object to be handled.
     * @return A ResponseEntity containing a message about the exception and HTTP status 404.
     * @author Kruti Panchal
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
