package com.group6.commune.Service;

import com.group6.commune.Model.Interest;
import com.group6.commune.Model.UserInterests;

import java.util.List;

public interface IInterestService {
    List<Interest> getInterestList();

    boolean addUserInterest(UserInterests userInterests);

    List<UserInterests> getInterestListByUserId (int user_id);
}
