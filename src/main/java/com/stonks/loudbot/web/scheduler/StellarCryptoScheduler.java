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
public class StellarCryptoScheduler extends CryptoScheduler {

    private static final Logger LOGGER = Logger.getLogger(StellarCryptoScheduler.class.getName());

    @Value("${threshold.crypto.stellar.gain}")
    private double thresholdGain;

    @Value("${threshold.crypto.stellar.loss}")
    private double thresholdLoss;

    @Autowired
    private Watcher stellarWatcher;

    @Autowired
    protected MessageSenderService whatsappMessageSender;

    private static final CryptoCurrency STELLAR = CryptoCurrency.STELLAR;

    @PostConstruct
    public void initStellarCryptoScheduler(){
        LOGGER.log(Level.INFO, STELLAR.getName() + " scheduler has started.");
        if(stellarWatcher.getCheckpoint() == 0){
            stellarWatcher.updateCheckpoint(getCryptoCurrentValue(CryptoCurrency.BITCOIN.getCode(), currency));
        }
    }

    @Override
    protected void scheduleCheck() {
        double currentValue = getCryptoCurrentValue(STELLAR.getCode(), currency);
        double diff = stellarWatcher.checkDiff(currentValue);
        checkDifference(diff, thresholdGain, thresholdLoss, currentValue, STELLAR, stellarWatcher, whatsappMessageSender);
    }

}
