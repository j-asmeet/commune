/**
 * This class represents the controller for managing community posts.
 * Author: Deepti Tuteja
 */
package com.group6.commune.Controller;

import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.UnauthorizedAccessException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.CommunityPosts;
import com.group6.commune.Service.CommunityPostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    CommunityPostServiceImpl communityPostService;

    /**
     * Retrieves a list of all community posts.
     *
     * @return A ResponseEntity containing a list of CommunityPosts objects and HTTP status 200 if successful.
     * @autho Deepti Tuteja
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/")
    public ResponseEntity<List<CommunityPosts>> getAllPosts(){
        return ResponseEntity.ok(communityPostService.getAllPosts());
    }

    /**
     * Creates a new community post.
     *
     * @param posts The CommunityPosts object representing the post to be created.
     * @param result The BindingResult containing potential validation errors.
     * @return A ResponseEntity containing the created CommunityPosts object and HTTP status 201 if successful.
     * @autho Deepti Tuteja
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<CommunityPosts> createPost(@RequestBody CommunityPosts posts, BindingResult result){
        return new ResponseEntity<>(communityPostService.createPosts(posts,result), HttpStatus.CREATED);
    }

    /**
     * Retrieves a specific community post by its ID.
     *
     * @param id The ID of the community post to be retrieved.
     * @return A ResponseEntity containing the CommunityPosts object with the given ID and HTTP status 200 if successful.
     * @autho Deepti Tuteja
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<CommunityPosts> getPosts(@PathVariable int id){
        return ResponseEntity.ok(communityPostService.getPostById(id));
    }

    /**
     * Retrieves a list of community posts by community ID.
     *
     * @param communityId The ID of the community whose posts are to be retrieved.
     * @return A ResponseEntity containing a list of CommunityPosts objects and HTTP status 200 if successful.
     * @autho Deepti Tuteja
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/community/{communityId}")
    public ResponseEntity<List<CommunityPosts>> getPostsByCommunity(@PathVariable int communityId){
        return ResponseEntity.ok(communityPostService.getPostByCommunityId(communityId));
    }

    /**
     * Updates an existing community post.
     *
     * @param posts The CommunityPosts object representing the post to be updated.
     * @param result The BindingResult containing potential validation errors.
     * @return The updated CommunityPosts object if successful, null if an error occurs during the operation.
     * @autho Deepti Tuteja
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(path = "/updatePost")
    public CommunityPosts updatePosts(@RequestBody CommunityPosts posts,BindingResult result){
        System.out.println("id"+ posts.getPostId());
        System.out.println(posts.getPostTitle());
        System.out.println(posts.getPostImage());
        return communityPostService.updatePost(posts,result);
//        return null;
    }

    /**
     * Deletes a community post by its ID.
     *
     * @param id The ID of the community post to be deleted.
     * @return A ResponseEntity containing the ID of the deleted post and HTTP status 200 if successful.
     * @autho Deepti Tuteja
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deletePosts(@PathVariable int id){
        return ResponseEntity.ok(communityPostService.deletePosts(id));
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
