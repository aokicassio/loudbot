package com.stonks.loudbot.web.scheduler;

import com.stonks.loudbot.model.Crypto;
import com.stonks.loudbot.web.service.CryptoCompareService;
import com.stonks.loudbot.web.service.MessageSenderService;
import com.stonks.loudbot.web.util.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class EthereumCryptoScheduler extends CryptoScheduler {

    private static final Logger LOGGER = Logger.getLogger(EthereumCryptoScheduler.class.getName());

    @Value("${crypto.currency.euro}")
    private String currency;

    @Value("${crypto.ethereum}")
    private String ethereumCode;

    @Autowired
    protected MessageSenderService whatsappMessageSender;

    @Autowired
    protected CryptoCompareService cryptoCompareService;

    @Override
    //@Scheduled(fixedRateString = "${scheduler.rate}", initialDelay = 2000)
    protected void scheduleCheck() {
        LOGGER.log(Level.INFO, "Ethereum Scheduler check triggered");

        Mono<String> bitcoin = cryptoCompareService.getCryptoCurrentPrice(ethereumCode, currency);
        String response = bitcoin.block();

        Crypto crypto = EntityMapper.parseCryptoFromJsonString(response.toLowerCase());

        sendMessage(whatsappMessageSender, String.format ("[%s] current price is %s %s", ethereumCode, currency, crypto.getEur()));
    }
}
