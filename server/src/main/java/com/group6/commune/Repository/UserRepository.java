package com.group6.commune.Repository;

import com.group6.commune.Model.User;
import org.springframework.http.ResponseEntity;

public interface UserRepository {

     ResponseEntity<User> getUserDetailsByID(int userId);

     ResponseEntity<String> createUserAccount(User user);

     ResponseEntity<String> updateAccountDetails(User user);
     ResponseEntity<String> deleteUserAccountById(int id);

     ResponseEntity<String> updatePassword(User user);

     User authenticateUserCredentials(String email);

}
