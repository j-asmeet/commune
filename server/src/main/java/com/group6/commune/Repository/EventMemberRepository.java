package com.group6.commune.Repository;

import com.group6.commune.Model.EventMembers;

import java.util.List;

public interface EventMemberRepository {
    List<EventMembers> getAllMembers(int eventId);
    EventMembers addMember(EventMembers eventMembers);
    EventMembers deleteMember(EventMembers eventMembers);
}
