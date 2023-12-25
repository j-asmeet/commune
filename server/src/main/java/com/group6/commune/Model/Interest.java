package com.group6.commune.Model;

public class Interest {
    private int interestId;
    private String interestName;
    private String interestCategory;

    public Interest() {}
    public Interest(int interestId, String interestName, String interestCategory) {
        this.interestId = interestId;
        this.interestName = interestName;
        this.interestCategory = interestCategory;
    }

    public int getInterestId() {
        return interestId;
    }

    public void setInterestId(int interestId) {
        this.interestId = interestId;
    }

    @Override
    public String toString() {
        return "Interest{" +
                "interestId=" + interestId +
                ", interestName='" + interestName + '\'' +
                ", interestCategory='" + interestCategory + '\'' +
                '}';
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public String getInterestCategory() {
        return interestCategory;
    }

    public void setInterestCategory(String interestCategory) {
        this.interestCategory = interestCategory;
    }

}
