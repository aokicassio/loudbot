package com.stonks.loudbot.web.scheduler;

import com.stonks.loudbot.model.Crypto;
import com.stonks.loudbot.model.CryptoCurrency;
import com.stonks.loudbot.web.service.CryptoCompareService;
import com.stonks.loudbot.web.service.MessageSenderService;
import com.stonks.loudbot.web.service.PhoneNumberService;
import com.stonks.loudbot.web.util.EntityMapper;
import com.stonks.loudbot.web.util.MessageTemplateUtil;
import com.stonks.loudbot.web.watcher.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class CryptoScheduler {
    private static final Logger LOGGER = Logger.getLogger(CryptoScheduler.class.getName());

    @Value("${config.currency}")
    protected String currency;

    @Autowired
    protected CryptoCompareService cryptoCompareService;

    @Autowired
    protected PhoneNumberService phoneNumberService;

    protected abstract void scheduleCheck();

    protected LocalDateTime lastCheckDate;

    protected void checkDifference(double diff, double thresholdGain, double thresholdLoss, double currentValue,
                                   CryptoCurrency cryptoCurrency, Watcher watcher, MessageSenderService messageSenderService){
        if(diff >= thresholdGain) {
            watcher.setCheckpoint(currentValue);
            watcher.setLastCheckpointDate(lastCheckDate);
            sendMessage(messageSenderService, MessageTemplateUtil.messageGain(
                    cryptoCurrency, thresholdGain, currency, watcher.getCheckpoint(), currentValue, lastCheckDate));
        } else if (diff <= thresholdLoss) {
            watcher.setCheckpoint(currentValue);
            sendMessage(messageSenderService, MessageTemplateUtil.messageLoss(
                    cryptoCurrency, thresholdLoss, currency, watcher.getCheckpoint(), currentValue, lastCheckDate));
        }
    }

    /**
     * Sends out messages to phone numbers on list
     * @param messageSenderService
     * @param body
     */
    protected void sendMessage(MessageSenderService messageSenderService, String body) {
        LOGGER.log(Level.INFO, "============================================================");
        LOGGER.log(Level.INFO, "Sending out messages");
        for(String phoneNumber : phoneNumberService.getPhoneNumbers()){
            LOGGER.log(Level.INFO, String.format("Sending message to %s ", phoneNumber));
            LOGGER.log(Level.INFO, body);
            //messageSenderService.sendMessage(body, phoneNumber);
        }
    }

    /**
     * Gets crypto current value, given code and currency
     * @param code
     * @param currency
     * @return
     */
    protected double getCryptoCurrentValue(String code, String currency){
        lastCheckDate = LocalDateTime.now();
        Mono<String> cryptoCoin = cryptoCompareService.getCryptoCurrentPrice(code, currency);
        String response = cryptoCoin.block();
        Crypto crypto = EntityMapper.parseCryptoFromJsonString(response.toLowerCase());

        return crypto.getEur();
    }

}
