package com.group6.commune.Service;

import com.group6.commune.Model.EventMembers;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface EventMemberService {
    EventMembers addMember(EventMembers eventMembers, BindingResult result);
    List<EventMembers> getAllMembers(int eventId);
    EventMembers deleteMember(EventMembers eventMembers);
}
