package com.group6.commune.Model;

import java.util.List;

public class UserInterests {
    private int userId;

    private List<Integer> interestIds;
    private int interest_id;

    public int getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(int interest_id) {
        this.interest_id = interest_id;
    }

    public UserInterests() {}

    @Override
    public String toString() {
        return "UserInterests{" +
                "userId=" + userId +
                ", interestList=" + interestIds +
                '}';
    }

    public List<Integer> getInterestIds() {
        return interestIds;
    }

    public void setInterestIds(List<Integer> interestList) {
        this.interestIds = interestList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserInterests(int userId, List<Integer> interestIds) {
        this.userId = userId;
        this.interestIds = interestIds;
    }
}
