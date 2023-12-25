/**
 * This class represents the controller for managing community members.
 * Author: Harsh Patel
 */
package com.group6.commune.Controller;

import com.group6.commune.AppLogger.AppLogger;
import com.group6.commune.Enums.UserRoles;
import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.UnauthorizedAccessException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.Member;
import com.group6.commune.Model.MemberResponse;
import com.group6.commune.Service.IMemberService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/community/{community_id}/members")
public class MemberController {
    @Autowired
    @Qualifier("MemberService")
    private IMemberService memberService;

    Logger logger = AppLogger.getLogger();

    /**
     * Adds a new member to the community.
     *
     * @param member The Member object representing the member to be added.
     * @return A Boolean indicating the success of the operation (true if successful, false otherwise).
     * @autho Harsh Patel
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public Boolean addMember(@RequestBody Member member) {
        logger.info("POST req on /community/{community_id}/members");
        return memberService.addMember(member);
    }

    /**
     * Retrieves a list of all members in the community by community ID.
     *
     * @param community_id The ID of the community whose members are to be retrieved.
     * @return A List of MemberResponse objects representing the members in the community.
     * @autho Harsh Patel
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<MemberResponse> getAllMember(@PathVariable int community_id) {
        logger.info("GET req on /community/community_id/members");
        return memberService.getAllMembers(community_id);
    }

    /**
     * Deletes a member from the community.
     *
     * @param member The Member object representing the member to be deleted.
     * @return A Boolean indicating the success of the operation (true if successful, false otherwise).
     * @autho Harsh Patel
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping
    public Boolean deleteMember(@RequestBody Member member) {
        logger.info("DELETE req on /community/community_id/members");
        return memberService.deleteMember(member);
    }

    /**
     * Changes the role of a member in the community.
     *
     * @param member   The Member object representing the member whose role is to be changed.
     * @param new_role The new role to be assigned to the member ("Member" or "Admin").
     * @return A Boolean indicating the success of the operation (true if successful, false otherwise).
     * @autho Harsh Patel
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping
    public Boolean changeUserRole(@RequestBody Member member, @RequestParam(name = "new_role") String new_role) {
        logger.info("PUT req on /community/community_id/members");
        UserRoles newRole = new_role.equalsIgnoreCase(UserRoles.Member.toString()) ? UserRoles.Member : UserRoles.Admin;
        return memberService.changeUserRole(member, newRole);
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
