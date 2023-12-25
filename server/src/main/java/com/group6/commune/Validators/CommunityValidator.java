package com.group6.commune.Validators;

import com.group6.commune.Model.Community;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CommunityValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Community.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required","Community Name should not be empty or null.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "field.required","Community description should not be empty or null.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "created_by", "field.required","Community creator should not be null or empty");
    }
}