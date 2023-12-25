package com.group6.commune.Model;

import java.util.Date;

public class Event {
    private int eventId;
    private String eventName;
    private String shortDescription;
    private String description;
    private String location;
    private Date eventStartTime;
    private Date eventEndTime;
    private String eventPoster;
    private int entryFees;

    private String eventType;

    private int createdByUserId;

    public Event(){}

    public Event(int eventId, String eventName, String shortDescription, String description, String location, Date eventStartTime, Date eventEndTime, String eventPoster, int entryFees, String eventType, int createdByUserId) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.shortDescription = shortDescription;
        this.description = description;
        this.location = location;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventPoster = eventPoster;
        this.entryFees = entryFees;
        this.eventType = eventType;
        this.createdByUserId = createdByUserId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(Date eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public Date getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(Date eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public String getEventPoster() {
        return eventPoster;
    }

    public void setEventPoster(String eventPoster) {
        this.eventPoster = eventPoster;
    }

    public int getEntryFees() {
        return entryFees;
    }

    public void setEntryFees(int entryFees) {
        this.entryFees = entryFees;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(int createdByUserId) {
        this.createdByUserId = createdByUserId;
    }


}

