/**
 * This class represents the controller for managing community comments.
 * Author: Deepti Tuteja
 */
package com.group6.commune.Controller;

import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.UnauthorizedAccessException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.CommunityComments;
import com.group6.commune.Service.CommunityCommentsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    CommunityCommentsServiceImpl communityCommentsService;

    /**
     * Creates a new community comment.
     *
     * @param comments The CommunityComments object containing the comment data.
     * @param result   The BindingResult object for validation.
     * @return A ResponseEntity containing the created CommunityComments object and HTTP status 201 if successful.
     * @throws ValidationException     If the input data fails validation.
     * @throws UnauthorizedAccessException If the user is not authorized to create the comment.
     * @author Deepti Tuteja
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<CommunityComments> createComment(@RequestBody CommunityComments comments, BindingResult result) {
        return new ResponseEntity<>(communityCommentsService.createComment(comments, result), HttpStatus.CREATED);
    }

    /**
     * Deletes a community comment by its ID.
     *
     * @param id The ID of the comment to be deleted.
     * @return A ResponseEntity containing the HTTP status 200 if the deletion is successful.
     * @throws DataNotFoundException If the comment with the given ID is not found.
     * @throws UnauthorizedAccessException If the user is not authorized to delete the comment.
     * @author Deepti Tuteja
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteComment(@PathVariable int id) {
        return ResponseEntity.ok(communityCommentsService.deleteComment(id));
    }

    /**
     * Retrieves a community comment by its ID.
     *
     * @param id The ID of the comment to be retrieved.
     * @return A ResponseEntity containing the retrieved CommunityComments object and HTTP status 200 if successful.
     * @throws DataNotFoundException If the comment with the given ID is not found.
     * @author Deepti Tuteja
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<CommunityComments> getCommentById(@PathVariable int id)  {
        return ResponseEntity.ok(communityCommentsService.getCommentsById(id));
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
