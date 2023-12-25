package com.group6.commune.Repository;

import com.group6.commune.Mapper.UserRowMapper;
import com.group6.commune.Model.User;
import com.group6.commune.Utils.IDgenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public ResponseEntity<User> getUserDetailsByID(int userId)
    {

        String query = "SELECT * FROM users WHERE user_id=?";

        User user= jdbcTemplate.queryForObject(query, new Object[]{userId}, new UserRowMapper());
        if (user != null)
            return new ResponseEntity<User>(user, HttpStatus.OK);
        else
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> createUserAccount(User user) {
        String query = "INSERT INTO users (user_id, first_name, last_name, dob, gender, email, " +
                "contact, password, profile_pic, enrollment_date) VALUES(?,?,?,?,?,?,?,?,?,?)";
        int id = IDgenerator.generateId();
        // Executing query
        int res = jdbcTemplate.update(query, new Object[]{id, user.getFirstName(), user.getLastName(), user.getDob(), user.getGender(), user.getEmail(),
                user.getContact(), user.getPassword(), user.getProfilePic(), user.getEnrollmentDate()});

        if(res == 1){
            return new ResponseEntity<>("User account got created successfully", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("User account not created", HttpStatus.BAD_REQUEST);
        }
    }

        @Override
        public ResponseEntity<String> updateAccountDetails(User user) {
            String query = "UPDATE users SET user_id = ?, first_name = ?, last_name = ?, dob = ?, gender = ?, email = ?," +
                    " contact = ?, password = ?, profile_pic = ?,enrollment_date = ?  WHERE user_id = ?";
            int res = jdbcTemplate.update(query, new Object[]{user.getUserId(), user.getFirstName(), user.getLastName(), user.getDob(), user.getGender(), user.getEmail(),
                    user.getContact(), user.getPassword(), user.getProfilePic(), user.getEnrollmentDate(), user.getUserId()});

            if(res == 1){
                return new ResponseEntity<>("User account details got updated successfully", HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }
        }

    public ResponseEntity<String> deleteUserAccountById(int id)
    {
        String query = "DELETE FROM users WHERE user_id=?";

        int res = jdbcTemplate.update(query, new Object[]{id});

        if(res == 1){
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<String> updatePassword(User user) {
        String query = "UPDATE users SET password = ?  WHERE email = ?";
        int res = jdbcTemplate.update(query, new Object[]{user.getPassword(), user.getEmail()});

        if(res == 1){
            return new ResponseEntity<>("Password updated successfully", HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public User authenticateUserCredentials(String email)
    {
        try {

            String query = "SELECT * FROM users WHERE email=?";

            User user = jdbcTemplate.queryForObject(query, new Object[]{email}, new UserRowMapper());
            return user;
        }
        catch(Exception e)
        {
            return null;
        }
    }


}
