package com.group6.commune.Repository;

import com.group6.commune.Model.Community;
import com.group6.commune.Model.Interest;
import com.group6.commune.Model.UserInterests;

import java.util.List;

public interface IInterestRepository {
    List<Interest> getInterestList();

    boolean saveUserInterest(int userId, int interestId);

    List<UserInterests> getInterestListByUserId(int user_id);
}
