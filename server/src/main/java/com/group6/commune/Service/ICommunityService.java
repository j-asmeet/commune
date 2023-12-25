package com.group6.commune.Service;

import com.group6.commune.Model.Community;
import com.group6.commune.Model.Interest;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ICommunityService {

    public int createCommunity(Community community, BindingResult result);
    public Community getCommunity(int communityID);
    public Boolean updateCommunity(Community community, BindingResult result);
    public Boolean deleteCommunity(int communityID);
    public List<Community> getAllCommunity();
    public List<Community> getAllCommunity(String keyword);
    public List<Community> getAllUserCommunity(int userID);
    public Boolean addCommunityInterest(int communityID, int interestID);
    public List<Interest> getCommunityInterests(int communityID);
    public Boolean deleteCommunityInterest(int communityID, int interestID);
}
