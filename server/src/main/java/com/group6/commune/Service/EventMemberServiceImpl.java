package com.group6.commune.Service;

import com.group6.commune.Exceptions.DataNotFoundException;
import com.group6.commune.Exceptions.ValidationException;
import com.group6.commune.Model.EventMembers;
import com.group6.commune.Repository.EventMemberRepositoryImpl;
import com.group6.commune.Repository.EventRepositoryImpl;
import com.group6.commune.Validators.EventMembersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventMemberServiceImpl implements EventMemberService {

    @Autowired
    EventMemberRepositoryImpl eventMemberRepository;

    @Autowired
    @Qualifier("eventMembersValidator")
    private EventMembersValidator eventMembersValidator;

    @Override
    public EventMembers addMember(EventMembers eventMembers, BindingResult result) {
        eventMembersValidator.validate(eventMembers, result);
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            throw new ValidationException("Provided event information is not valid", errors);
        }
        return eventMemberRepository.addMember(eventMembers);
    }

    @Override
    public List<EventMembers> getAllMembers(int eventId) {
        List<EventMembers> eventMembers = eventMemberRepository.getAllMembers(eventId);
        if (eventMembers.isEmpty() || eventMembers.size() <=0){
            throw new DataNotFoundException("No users have registered for this event");
        }
        return eventMembers;
    }

    @Override
    public EventMembers deleteMember(EventMembers eventMembers) {
        if (eventMembers == null) {
            throw new DataNotFoundException("Event Member does not exist");
        }
        EventMembers eventMembers1 =  eventMemberRepository.deleteMember(eventMembers);
        if (eventMembers1 == null){
            throw new DataNotFoundException("Event member does not exist.");
        }
        return eventMembers1;
    }
}
