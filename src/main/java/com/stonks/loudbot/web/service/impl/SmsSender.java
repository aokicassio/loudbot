package com.stonks.loudbot.web.service.impl;

import com.stonks.loudbot.web.service.MessageSenderService;
import org.springframework.beans.factory.annotation.Value;

public class SmsSender implements MessageSenderService {

    @Value("${sms.number.from}")
    protected String phoneNumberFrom;

    @Override
    public void sendMessage(String body, String phoneNumberTo) {
        //TODO Implement third party SMS service provider
    }
}
