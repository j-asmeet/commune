/**
 * This class represents the controller for managing interests.
 * Author: Mehulkumar Bhunsadiya
 */
package com.group6.commune.Controller;

import com.group6.commune.AppLogger.AppLogger;
import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.UnauthorizedAccessException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.Interest;
import com.group6.commune.Model.UserInterests;
import com.group6.commune.Service.InterestServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/interest")
public class InterestController {
    @Autowired
    private InterestServiceImpl interestService;
    Logger logger = AppLogger.getLogger();

    /**
     * Retrieves the list of all interests.
     *
     * @return A ResponseEntity containing a list of Interest objects and HTTP status 200 if successful.
     * @autho Mehulkumar Bhunsadiya
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<Interest>> getInterestList() {
        logger.info("GET req on /interest");
        return ResponseEntity.ok(interestService.getInterestList());
    }

    /**
     * Adds user interests.
     *
     * @param userInterests The UserInterests object containing the user's interests.
     * @return A ResponseEntity containing a success message and HTTP status 201 if successful,
     *         or an error message and HTTP status 500 if an error occurs during the operation.
     * @autho Mehulkumar Bhunsadiya
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<String> addUserInterests(@RequestBody UserInterests userInterests) {
        try {
            interestService.addUserInterest(userInterests);
            logger.info("POST req on /interest");
            return ResponseEntity.status(HttpStatus.CREATED).body("User interests added successfully.");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding user interests.");
        }
    }

    /**
     * Adds user interests.
     *
     * @param user_id user_id of the logged user.
     * @return A ResponseEntity containing a success message and HTTP status 201 if successful,
     *         or an error message and HTTP status 500 if an error occurs during the operation.
     * @autho Mehulkumar Bhunsadiya
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{user_id}")
    public ResponseEntity<List<UserInterests>> getAllCommunity(@PathVariable int user_id) {
        logger.info("GET req on /interest" + user_id);
        return ResponseEntity.ok(interestService.getInterestListByUserId(user_id));
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
