package com.group6.commune.ServiceTests;

import com.group6.commune.Model.EmailDetails;

import com.group6.commune.Model.LoginResponseDTO;

import com.group6.commune.Model.User;

import com.group6.commune.Repository.EmailTemplateRepositoryImpl;

import com.group6.commune.Repository.UserRepository;

import com.group6.commune.Security.jwtTokenGeneration;

import com.group6.commune.Service.UserServiceImpl;

import com.group6.commune.Utils.CommuneEmailAgent;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Date;


import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;


public class UserServiceTest {

    @Mock

    private UserRepository userRepository;


    @Mock

    private EmailTemplateRepositoryImpl emailTemplateRepo;


    @Mock

    private CommuneEmailAgent mailAgent;


    @InjectMocks

    private UserServiceImpl userService;


    @Mock

    PasswordEncoder encoder;


    @Mock

    private AuthenticationManager authenticationManager;


    @Mock

    private jwtTokenGeneration tokenService;



    @BeforeEach

    public void setup() {

        MockitoAnnotations.openMocks(this);

    }


    @Test

    public void testGetUserDetailsById() {

        User user = new User(1, "John", "Doe", new Date(), "Male", "john.doe@example.com",

                "1234567890", "password123", "profilePic.jpg", new Date());


        ResponseEntity<User> response=new ResponseEntity<>(user, HttpStatus.OK);

        when(userRepository.getUserDetailsByID(1)).thenReturn(response);


        ResponseEntity<User> result = userService.getUserDetailsById(1);

        assertEquals(result.getBody().getUserId(), 1);
        assertEquals(result.getBody().getFirstName(),"John");
        assertEquals(result.getBody().getLastName(),"Doe");
        assertEquals(result.getBody().getEmail(),"john.doe@example.com");

    }


    @Test

    public void testCreateUser() {

        User user = new User(1, "John", "Doe", new Date(), "Male", "john.doe@example.com",

                "1234567890", "password123", "profilePic.jpg", new Date());


        ResponseEntity<String> response=new ResponseEntity<>("User account got created successfully", HttpStatus.CREATED);
        when(userRepository.createUserAccount(user)).thenReturn(response);


        EmailDetails emailDetails = new EmailDetails("","","","");
        when(emailTemplateRepo.getEmailDetailsFromDB(1)).thenReturn(emailDetails);

        when(mailAgent.sendSimpleMail(emailDetails)).thenReturn("Mail Sent Successfully...");


        ResponseEntity<String> result = userService.createUser(user);

        assertEquals(HttpStatus.CREATED,result.getStatusCode());


    }


    @Test

    public void testUpdateAccountDetails() {

        User user = new User(1, "John", "Doe", new Date(), "Male", "john.doe@example.com",

                "1234567890", "password123", "profilePic.jpg", new Date());


        ResponseEntity<String> response=new ResponseEntity<>("User account details got updated successfully", HttpStatus.ACCEPTED);
        when(userRepository.updateAccountDetails(any(User.class))).thenReturn(response);


        ResponseEntity<String> result = userService.updateAccountDetails(user);

        assertEquals(HttpStatus.ACCEPTED,result.getStatusCode());

    }


    @Test

    public void testDeleteUserAccountById() {

        ResponseEntity<String> response=new ResponseEntity<>("User deleted successfully", HttpStatus.OK);

        when(userRepository.deleteUserAccountById(1)).thenReturn(response);


        ResponseEntity<String> result = userService.deleteUserAccountById(1);

        assertEquals(HttpStatus.OK,result.getStatusCode());

    }


    @Test

    public void testCreateVerificationCode() {

        String email = "test@example.com";

        EmailDetails emailDetails = new EmailDetails("",",","","");

        emailDetails.setMailBody("Verification code: ");

// Set other email details

        when(emailTemplateRepo.getEmailDetailsFromDB(2)).thenReturn(emailDetails);
        when(mailAgent.sendSimpleMail(emailDetails)).thenReturn("Success");


        int result = userService.createVerificationCode(email);

        assertNotNull(result);
        verify(emailTemplateRepo).getEmailDetailsFromDB(2);
        verify(mailAgent).sendSimpleMail(any(EmailDetails.class));

    }


    @Test

    public void testUpdateUserPassword() {

        User user = new User();

        user.setPassword("password"); // Set the password

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Password updated successfully");

        when(encoder.encode(anyString())).thenReturn("encoded-password");
        when(userRepository.updatePassword(any(User.class))).thenReturn(expectedResponse);


        ResponseEntity<String> responseEntity = userService.updateUserPassword(user);

        assertEquals(expectedResponse, responseEntity);
        verify(encoder).encode(anyString());
        verify(userRepository).updatePassword(any(User.class));

    }


    @Test

    public void testLoginUser() {

        String username = "testuser";

        String password = "testpassword";

        User user = new User();

        user.setUserId(1);

        user.setFirstName("Test");

        user.setLastName("User");


        Authentication mockAuthentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))

                .thenReturn(mockAuthentication);
        when(tokenService.generateJWTToken(any(Authentication.class), eq(1)))

                .thenReturn("test-token");

        when(mockAuthentication.getPrincipal()).thenReturn(user); // Mock the getPrincipal() to return the user object


        LoginResponseDTO result = userService.loginUser(username, password);

        assertEquals(user.getUserId(), result.getUserId());
        assertEquals("Test User", result.getName());
        assertEquals("test-token", result.getBearerToken());

    }


}
