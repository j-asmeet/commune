package com.group6.commune.Repository;

import com.group6.commune.Model.EmailDetails;
import com.group6.commune.Model.User;

public interface EmailTemplateRepository {

    public EmailDetails getEmailDetailsFromDB(int id);

}
