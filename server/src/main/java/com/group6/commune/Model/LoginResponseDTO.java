package com.group6.commune.Model;

public class LoginResponseDTO {
    private int userId;
    private String name;
    private String BearerToken;

    public LoginResponseDTO(int userId, String name, String bearerToken) {
        this.userId = userId;
        this.name = name;
        BearerToken = bearerToken;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getBearerToken() {
        return BearerToken;
    }
}
