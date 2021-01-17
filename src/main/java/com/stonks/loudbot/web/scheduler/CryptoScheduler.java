package com.stonks.loudbot.web.scheduler;

import com.stonks.loudbot.web.service.MessageSenderService;
import com.stonks.loudbot.web.service.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class CryptoScheduler {
    private static final Logger LOGGER = Logger.getLogger(CryptoScheduler.class.getName());

    @Value("${config.currency}")
    protected String currency;

    @Autowired
    protected PhoneNumberService phoneNumberService;

    protected abstract void scheduleCheck();

    /**
     * Sends out messages to phone numbers on list
     * @param messageSenderService
     * @param body
     */
    protected void sendMessage(MessageSenderService messageSenderService, String body) {
        LOGGER.log(Level.INFO, "Sending out messages");
        for(String phoneNumber : phoneNumberService.getPhoneNumbers()){
            LOGGER.log(Level.INFO, String.format ("Sending message to %s ", phoneNumber));
            messageSenderService.sendMessage(body, phoneNumber);
        }
    }

}
