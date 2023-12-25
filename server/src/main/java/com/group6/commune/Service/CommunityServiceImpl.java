package com.group6.commune.Service;

import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.Community;
import com.group6.commune.Model.Interest;
import com.group6.commune.Repository.ICommunityRepository;
import com.group6.commune.Validators.CommunityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Qualifier("CommunityService")
public class CommunityServiceImpl implements ICommunityService{

    @Autowired
    @Qualifier("CommunityRepository")
    private ICommunityRepository communityRepository;

    private CommunityValidator communityValidator = new CommunityValidator();

    @Override
    public int createCommunity(Community community, BindingResult result){
        if(community == null){
            throw new DataNotFoundException("Null community object!");
        }

        communityValidator.validate(community, result);
        if(result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            throw new ValidationException("Provided event information is not valid", errors);
        }
        return communityRepository.createCommunity(community);
    }

    @Override
    public Community getCommunity(int communityID){
        Community community;
        try{
            community = communityRepository.getCommunity(communityID);
        } catch (EmptyResultDataAccessException e){
            throw new DataNotFoundException("Not found!");
        }
        return community;
    }

    @Override
    public Boolean updateCommunity(Community community, BindingResult result){
        if(community == null){
            throw new DataNotFoundException("Null community object!");
        }

        communityValidator.validate(community, result);
        if(result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            throw new ValidationException("Provided event information is not valid", errors);
        }
        return communityRepository.updateCommunity(community);
    }
    @Override
    public Boolean deleteCommunity(int communityID){
        return communityRepository.deleteCommunity(communityID);
    }
    @Override
    public List<Community> getAllCommunity(){
        return communityRepository.getAllCommunity();
    }
    @Override
    public List<Community> getAllCommunity(String keyword){
        return communityRepository.getAllCommunity(keyword);
    }

    @Override
    public List<Community> getAllUserCommunity(int userID) {
        return communityRepository.getAllUserCommunity(userID);
    }

    @Override
    public Boolean addCommunityInterest(int communityID, int interestID) {
        return communityRepository.addCommunityInterest(communityID, interestID);
    }

    @Override
    public List<Interest> getCommunityInterests(int communityID) {
        return communityRepository.getCommunityInterests(communityID);
    }

    @Override
    public Boolean deleteCommunityInterest(int communityID, int interestID) {
        return communityRepository.deleteCommunityInterest(communityID, interestID);
    }
}
