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
public class ZeroXCryptoScheduler extends CryptoScheduler {

    private static final Logger LOGGER = Logger.getLogger(ZeroXCryptoScheduler.class.getName());

    @Value("${threshold.crypto.stellar.gain}")
    private double thresholdGain;

    @Value("${threshold.crypto.stellar.loss}")
    private double thresholdLoss;

    @Autowired
    private Watcher eosWatcher;

    @Autowired
    protected MessageSenderService whatsappMessageSender;

    public static final CryptoCurrency ZERO_X = CryptoCurrency.ZERO_X;

    @PostConstruct
    public void initZeroXCryptoScheduler(){
        LOGGER.log(Level.INFO, ZERO_X.getName() + " scheduler has started.");
        if(eosWatcher.getCheckpoint() == 0){
            eosWatcher.updateCheckpoint(getCryptoCurrentValue(ZERO_X.getCode(), currency));
        }
    }

    @Override
    protected void scheduleCheck() {
        double currentValue = getCryptoCurrentValue(ZERO_X.getCode(), currency);
        double diff = eosWatcher.checkDiff(currentValue);
        checkDifference(diff, thresholdGain, thresholdLoss, currentValue, ZERO_X, eosWatcher, whatsappMessageSender);
    }

}
