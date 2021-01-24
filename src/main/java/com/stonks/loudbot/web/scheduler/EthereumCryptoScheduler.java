package com.stonks.loudbot.web.scheduler;

import com.stonks.loudbot.model.CryptoCurrency;
import com.stonks.loudbot.web.service.CryptoCompareService;
import com.stonks.loudbot.web.service.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class EthereumCryptoScheduler extends CryptoScheduler {

    private static final Logger LOGGER = Logger.getLogger(EthereumCryptoScheduler.class.getName());

    @Autowired
    protected MessageSenderService whatsappMessageSender;

    @Autowired
    protected CryptoCompareService cryptoCompareService;

    private static final CryptoCurrency ETHEREUM = CryptoCurrency.ETHEREUM;

    @Override
    protected void scheduleCheck() {
        LOGGER.log(Level.INFO, "Ethereum Scheduler check triggered");
        double currentValue = getCryptoCurrentValue(ETHEREUM.getCode(), currency);

        sendMessage(whatsappMessageSender, String.format ("[%s] %s current price is %s %.2f",
                ETHEREUM.getCode(), ETHEREUM.getName(), currency, currentValue));
    }
}
