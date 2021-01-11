package com.stonks.loudbot.web.service.impl;

import com.stonks.loudbot.web.service.MessageSenderService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsappMessageSender implements MessageSenderService {

    @Value("${twilio.acountid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${whatsapp.number.from}")
    protected String phoneNumberFrom;

    @Override
    public void sendMessage(String body, String phoneNumberTo) {
        Twilio.init(accountSid, authToken);
        Message.creator(
                new com.twilio.type.PhoneNumber(phoneNumberTo),
                new com.twilio.type.PhoneNumber(phoneNumberFrom),
                body)
                .create();
    }
}
