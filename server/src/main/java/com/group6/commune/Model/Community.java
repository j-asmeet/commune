package com.group6.commune.Model;

import org.springframework.stereotype.Component;

/**
 * Represents a community object
 * */

@Component
public class Community {
    private int community_id;
    private int created_by;
    private String name;
    private String description;
    private String display_image;

    public Community(){

    }

    public Community(int community_id, int created_by, String name, String description, String display_image) {
        this.community_id = community_id;
        this.created_by = created_by;
        this.name = name;
        this.description = description;
        this.display_image = display_image;
    }

    public int getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(int community_id) {
        this.community_id = community_id;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplay_image() {
        return display_image;
    }

    public void setDisplay_image(String display_image) {
        this.display_image = display_image;
    }
}
