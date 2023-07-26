package com.invogue_fashionblog.services.serviceimpl;

public interface EmailMailSenderService {
    void sendEmail(String toEmail,
                   String subject,
                   String body);
}
