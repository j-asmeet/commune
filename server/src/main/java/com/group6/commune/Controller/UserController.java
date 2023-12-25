/**
 * This class represents the controller for managing user accounts and authentication.
 * Author: Jasmeet Singh
 */
package com.group6.commune.Controller;

import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.UnauthorizedAccessException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.LoginResponseDTO;
import com.group6.commune.Model.User;
import com.group6.commune.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Creates a new user account.
     *
     * @param user The User object representing the account to be created.
     * @return A ResponseEntity containing a message and HTTP status 200 if successful, or an error message and HTTP status 400/500 in case of failure.
     * @author Jasmeet Singh
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/Signup")
    public ResponseEntity<String> createAccount(@RequestBody User user){
        return userService.createUser(user);
    }

    /**
     * Retrieves user details by ID.
     *
     * @param id The ID of the user whose details are to be retrieved.
     * @return A ResponseEntity containing the User object with the given ID and HTTP status 200 if successful, or an error message and HTTP status 404 in case of failure.
     * @author Jasmeet Singh
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserDetailsById(@PathVariable int id){
        return userService.getUserDetailsById(id);
    }

    /**
     * Updates user account details.
     *
     * @param user The User object representing the account details to be updated.
     * @return A ResponseEntity containing a message and HTTP status 200 if successful, or an error message and HTTP status 400/500 in case of failure.
     * @author Jasmeet Singh
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping
    public ResponseEntity<String> updateUserDetails(@RequestBody User user){
        return userService.updateAccountDetails(user);
    }

    /**
     * Deletes a user account by its ID.
     *
     * @param id The ID of the user account to be deleted.
     * @return A ResponseEntity containing a message and HTTP status 200 if successful, or an error message and HTTP status 400/500 in case of failure.
     * @author Jasmeet Singh
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserByUserId(@PathVariable int id){
        return userService.deleteUserAccountById(id);
    }

    /**
     * Generates a verification code for password reset.
     *
     * @param email The email of the user for whom the verification code needs to be generated.
     * @return The verification code as an integer value if successful, or 0 in case of failure or user not found.
     * @autho Jasmeet Singh
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/forgotPassword")
    public int createVerificationCode(@RequestParam String email){
        return userService.createVerificationCode(email);
    }

    /**
     * Updates the user password using the verification code.
     *
     * @param user The User object containing the new password and verification code.
     * @return A ResponseEntity containing a message and HTTP status 200 if successful, or an error message and HTTP status 400/500 in case of failure.
     * @autho Jasmeet Singh
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/resetPassword")
    public ResponseEntity<String> updateUserPassword(@RequestBody User user)
    {
        return userService.updateUserPassword(user);
    }

    /**
     * Generates an authentication token for user login.
     *
     * @param body The User object containing the login credentials (email and password).
     * @return A LoginResponseDTO object containing the authentication token and user information if successful, or null in case of login failure.
     * @autho Jasmeet Singh
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public LoginResponseDTO generateToken(@RequestBody User body){
        return userService.loginUser(body.getEmail(), body.getPassword());
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
