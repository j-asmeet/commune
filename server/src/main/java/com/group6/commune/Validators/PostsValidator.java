package com.group6.commune.Validators;

import com.group6.commune.Model.Community;
import com.group6.commune.Model.CommunityPosts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PostsValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CommunityPosts.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postTitle", "field.required","Post Name should not be empty or null.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "field.required","Post description should not be empty or null.");
    }
}