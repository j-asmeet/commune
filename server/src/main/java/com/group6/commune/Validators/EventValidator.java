package com.group6.commune.Validators;

import com.group6.commune.Model.Event;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EventValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Event.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventName", "field.required","Event Name should not be empty or null.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "field.required","Event description Name should not be empty or null.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "field.required","Event location should not be empty or null.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventStartTime", "field.required","Event start time should not be null or empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventEndTime", "field.required","Event end time should not be null or empty.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventType", "field.required", "Event type should not be null or empty.");

        Event event = (Event) target;
        if (event.getCreatedByUserId() <= 0) {
            errors.rejectValue("createdByUserId", "field.required","User id should be greater than zero.");
        }
    }
}
