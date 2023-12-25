package com.group6.commune.Validators;

import com.group6.commune.Model.CommunityComments;
import com.group6.commune.Model.CommunityPosts;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CommentsValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CommunityComments.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "comment", "field.required","Comment should not be empty or null.");
    }
}
