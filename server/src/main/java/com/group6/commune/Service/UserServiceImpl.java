package com.group6.commune.Service;

import com.group6.commune.Model.EmailDetails;
import com.group6.commune.Model.LoginResponseDTO;
import com.group6.commune.Model.User;
import com.group6.commune.Repository.EmailTemplateRepositoryImpl;
import com.group6.commune.Repository.UserRepository;
import com.group6.commune.Security.jwtTokenGeneration;
import com.group6.commune.Utils.CommuneEmailAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailTemplateRepositoryImpl emailTemplateRepo;

    @Autowired
    CommuneEmailAgent mailAgent;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private jwtTokenGeneration tokenService;

    @Override
    public ResponseEntity<User> getUserDetailsById(int userId){
        return userRepository.getUserDetailsByID(userId);
    }

    @Override
    public ResponseEntity<String> createUser(User user){
        user.setPassword(encoder.encode(user.getPassword()));
       ResponseEntity<String> response= userRepository.createUserAccount(user);
        if(response.getStatusCode()== HttpStatus.CREATED)
        {
            EmailDetails emailDetails=emailTemplateRepo.getEmailDetailsFromDB(1);
            emailDetails.setRecipient(user.getEmail());
            mailAgent.sendSimpleMail(emailDetails) ;
        }
        return response;
    }

    @Override
    public ResponseEntity<String> updateAccountDetails(User user)
    {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.updateAccountDetails(user);
    }

    @Override
    public ResponseEntity<String> deleteUserAccountById(int id)
    {
        return userRepository.deleteUserAccountById(id);
    }

    @Override
    public int createVerificationCode(String email){
        EmailDetails emailDetails=emailTemplateRepo.getEmailDetailsFromDB(2);
        emailDetails.setRecipient(email);
        int verificationCode=new Random().nextInt(900000) + 100000;
        emailDetails.setMailBody(emailDetails.getMailBody()+verificationCode+"<br><br>Thanks and Regards,<br>Commune Team");
        mailAgent.sendSimpleMail(emailDetails) ;
        return verificationCode;
    }

    @Override
    public ResponseEntity<String> updateUserPassword(User user)
    {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.updatePassword(user);
    }

    public LoginResponseDTO loginUser(String username, String password){

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            User user=(User)auth.getPrincipal();
            String token = tokenService.generateJWTToken(auth, user.getUserId());


            return new LoginResponseDTO(user.getUserId(),user.getFirstName()+ " "+user.getLastName(), token);

        } catch(AuthenticationException e){
            return null;
        }
    }


}
