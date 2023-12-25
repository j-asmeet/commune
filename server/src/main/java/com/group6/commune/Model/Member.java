package com.group6.commune.Model;

import com.group6.commune.Enums.UserRoles;

/**
 * Represents a Member Object
 * */
public class Member {
    private int community_id;
    private int user_id;
    private UserRoles user_role;

    public Member(){

    }

    public Member(int community_id, int user_id, UserRoles user_role){
        this.community_id = community_id;
        this.user_id = user_id;
        this.user_role = user_role;
    }

    public int getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(int community_id) {
        this.community_id = community_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public UserRoles getUser_role() {
        return user_role;
    }

    public void setUser_role(UserRoles user_role) {
        this.user_role = user_role;
    }
}
