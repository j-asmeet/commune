package com.group6.commune.Validators;

import com.group6.commune.Model.EventMembers;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EventMembersValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        EventMembers eventMembers = (EventMembers) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventId", "field.required","Event id should not be empty or null");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "field.required","Event id should not be empty or null");
        if (eventMembers.getEventId() == 0 || eventMembers.getEventId() <0 ){
            errors.rejectValue("eventId", "field.required","Event Id should be greater than 0.");

        }
        if (eventMembers.getUserId() == 0 || eventMembers.getUserId() <0 ){
            errors.rejectValue("eventId", "field.required","Event Id should be greater than 0.");

        }
    }
}
