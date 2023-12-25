package com.group6.commune.Model;

public class EmailDetails {


        private String recipient;
        private String sender;
        private String mailBody;
        private String mailSubject;

    public EmailDetails(String recipient, String sender, String mailBody, String mailSubject) {
        this.recipient = recipient;
        this.sender = sender;
        this.mailBody = mailBody;
        this.mailSubject = mailSubject;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMailBody() {
        return mailBody;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setMailBody(String mailBody) {
        this.mailBody = mailBody;
    }
}
