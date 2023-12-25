package com.group6.commune.Service;

import com.group6.commune.Model.LoginResponseDTO;
import com.group6.commune.Model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
     ResponseEntity<User> getUserDetailsById(int userId);

     ResponseEntity<String> createUser(User user);

     ResponseEntity<String> updateAccountDetails(User user);

     ResponseEntity<String> deleteUserAccountById(int userId);

     int createVerificationCode(String email);

     ResponseEntity<String> updateUserPassword(User user);

     LoginResponseDTO loginUser(String username, String password);

}
