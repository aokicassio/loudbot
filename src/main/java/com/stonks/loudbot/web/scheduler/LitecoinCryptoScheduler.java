package com.stonks.loudbot.web.scheduler;

import com.stonks.loudbot.model.CryptoCurrency;
import com.stonks.loudbot.web.service.MessageSenderService;
import com.stonks.loudbot.web.watcher.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class LitecoinCryptoScheduler extends CryptoScheduler {

    private static final Logger LOGGER = Logger.getLogger(LitecoinCryptoScheduler.class.getName());

    @Value("${threshold.crypto.litecoin.gain}")
    private double thresholdGain;

    @Value("${threshold.crypto.litecoin.loss}")
    private double thresholdLoss;

    @Autowired
    private Watcher litecoinWatcher;

    @Autowired
    protected MessageSenderService whatsappMessageSender;

    private static final CryptoCurrency LITECOIN = CryptoCurrency.LITECOIN;

    @PostConstruct
    public void initLitecoinCryptoScheduler(){
        LOGGER.log(Level.INFO, LITECOIN.getName() + " scheduler has started.");
        if(litecoinWatcher.getCheckpoint() == 0){
            litecoinWatcher.updateCheckpoint(getCryptoCurrentValue(LITECOIN.getCode(), currency));
        }
    }

    @Override
    protected void scheduleCheck() {
        double currentValue = getCryptoCurrentValue(LITECOIN.getCode(), currency);
        double diff = litecoinWatcher.checkDiff(currentValue);
        checkDifference(diff, thresholdGain, thresholdLoss, currentValue, LITECOIN, litecoinWatcher, whatsappMessageSender);
    }

}
