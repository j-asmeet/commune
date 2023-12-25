/**
 * This class represents the controller for managing communities.
 * Author: Harsh Patel
 */
package com.group6.commune.Controller;

import com.group6.commune.AppLogger.AppLogger;
import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.UnauthorizedAccessException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.Community;
import com.group6.commune.Model.Interest;
import com.group6.commune.Service.ICommunityService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    @Qualifier("CommunityService")
    private ICommunityService communityService;

    Logger logger = AppLogger.getLogger();

    /**
     * Creates a new community.
     *
     * @param community The Community object containing the community data.
     * @param result    The BindingResult object for validation.
     * @return A ResponseEntity containing the ID of the created community and HTTP status 200 if successful.
     * @throws ValidationException If the input data fails validation.
     * @author Harsh Patel
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<Integer> createCommunity(@RequestBody Community community, BindingResult result) {
        logger.info("POST req on /community");
        return ResponseEntity.ok(communityService.createCommunity(community, result));
    }

    /**
     * Retrieves a community by its ID.
     *
     * @param id The ID of the community to be retrieved.
     * @return A ResponseEntity containing the retrieved Community object and HTTP status 200 if successful.
     * @throws DataNotFoundException If the community with the given ID is not found.
     * @author Harsh Patel
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Community> getCommunity(@PathVariable int id) {
        logger.info("GET req on /community/" + id);
        return new ResponseEntity<>(communityService.getCommunity(id), HttpStatus.OK);
    }

    /**
     * Retrieves all communities or communities matching a specific keyword.
     *
     * @param keyword The optional keyword to filter communities by name or description.
     * @return A ResponseEntity containing a list of Community objects and HTTP status 200 if successful.
     * @throws DataNotFoundException If no communities are found with the given keyword.
     * @author Harsh Patel
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<Community>> getAllCommunity(@RequestParam(required = false, name = "keyword") String keyword) {
        logger.info("GET req on /community");
        if (keyword == null || keyword.isEmpty()) {
            return ResponseEntity.ok(communityService.getAllCommunity());
        } else {
            return ResponseEntity.ok(communityService.getAllCommunity(keyword));
        }
    }

    /**
     * Updates an existing community.
     *
     * @param community The Community object containing the updated community data.
     * @param result    The BindingResult object for validation.
     * @return A ResponseEntity containing true if the update is successful and HTTP status 200 if successful.
     * @throws ValidationException If the input data fails validation.
     * @author Harsh Patel
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping
    public ResponseEntity<Boolean> updateCommunity(@RequestBody Community community, BindingResult result)  {
        logger.info("PUT req on /community");
        return ResponseEntity.ok(communityService.updateCommunity(community, result));
    }

    /**
     * Deletes a community by its ID.
     *
     * @param id The ID of the community to be deleted.
     * @return A ResponseEntity containing true if the deletion is successful and HTTP status 200 if successful.
     * @throws DataNotFoundException If the community with the given ID is not found.
     * @throws UnauthorizedAccessException If the user is not authorized to delete the community.
     * @author Harsh Patel
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCommunity(@PathVariable int id) {
        logger.info("DELETE req on /community/" + id);
        return ResponseEntity.ok(communityService.deleteCommunity(id));
    }

    /**
     * Retrieves all communities belonging to a specific user.
     *
     * @param user_id The ID of the user whose communities are to be retrieved.
     * @return A ResponseEntity containing a list of Community objects and HTTP status 200 if successful.
     * @throws DataNotFoundException If the user has no communities.
     * @throws UnauthorizedAccessException If the user is not authorized to access the communities of the given user.
     * @author Harsh Patel
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Community>> getAllCommunity(@PathVariable int user_id) {
        logger.info("GET req on /community/user/" + user_id);
        return ResponseEntity.ok(communityService.getAllUserCommunity(user_id));
    }

    /**
     * Adds an interest to a community.
     *
     * @param id          The ID of the community to which the interest will be added.
     * @param interest_id The ID of the interest to be added.
     * @return A ResponseEntity containing true if the addition is successful and HTTP status 200 if successful.
     * @throws DataNotFoundException If the community or interest with the given ID is not found.
     * @throws UnauthorizedAccessException If the user is not authorized to add interests to the community.
     * @throws ValidationException If the input data fails validation.
     * @author Harsh Patel
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/{id}/interest")
    public ResponseEntity<Boolean> addCommunityInterest(@PathVariable int id, @RequestParam(required = true, name = "interest_id") int interest_id) {
        logger.info("POST req on /community/" + id + "/interest");
        return ResponseEntity.ok(communityService.addCommunityInterest(id, interest_id));
    }

    /**
     * Retrieves all interests of a community by its ID.
     *
     * @param id The ID of the community whose interests are to be retrieved.
     * @return A ResponseEntity containing a list of Interest objects and HTTP status 200 if successful.
     * @throws DataNotFoundException If the community with the given ID is not found.
     * @throws UnauthorizedAccessException If the user is not authorized to access the interests of the community.
     * @throws ValidationException If the input data fails validation.
     * @author Harsh Patel
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}/interest")
    public ResponseEntity<List<Interest>> getCommunityInterests(@PathVariable int id){
        logger.info("GET req on /community/" + id + "/interest");
        return ResponseEntity.ok(communityService.getCommunityInterests(id));
    }

    /**
     * Deletes an interest from a community.
     *
     * @param id          The ID of the community from which the interest will be deleted.
     * @param interest_id The ID of the interest to be deleted.
     * @return A ResponseEntity containing true if the deletion is successful and HTTP status 200 if successful.
     * @throws DataNotFoundException If the community or interest with the given ID is not found.
     * @throws UnauthorizedAccessException If the user is not authorized to delete interests from the community.
     * @throws ValidationException If the input data fails validation.
     * @author Harsh Patel
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}/interest")
    public ResponseEntity<Boolean> deleteCommunity(@PathVariable int id, @RequestParam(required = false, name = "interest_id") int interest_id) {
        logger.info("DELETE req on /community/" + id + "/interest");
        return ResponseEntity.ok(communityService.deleteCommunityInterest(id, interest_id));
    }

    /**
     * Handles ValidationException and returns a ResponseEntity with an error message and validation errors.
     *
     * @param ex The ValidationException object containing the exception message and validation errors.
     * @return A ResponseEntity containing the error message, validation errors, and HTTP status 400.
     * @author Harsh Patel
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage(), "errors", ex.getErrors()));
    }

    /**
     * Handles UnauthorizedAccessException and returns a ResponseEntity with an error message.
     *
     * @param ex The UnauthorizedAccessException object containing the exception message.
     * @return A ResponseEntity containing the error message and HTTP status 401.
     * @autho Harsh Patel
     */
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", ex.getMessage()));
    }


    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDataNotFoundException(DataNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", ex.getMessage()));
    }


}
