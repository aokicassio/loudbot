package com.stonks.loudbot.web.scheduler;

import com.stonks.loudbot.model.CryptoCurrency;
import com.stonks.loudbot.web.service.MessageSenderService;
import com.stonks.loudbot.web.watcher.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StellarCryptoScheduler extends CryptoScheduler {

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
        initWatcher(STELLAR, stellarWatcher);
    }

    @Override
    protected void scheduleCheck() {
        double currentValue = getCryptoCurrentValue(STELLAR.getCode(), currency);
        double diff = stellarWatcher.checkDiff(currentValue);
        compareWithThresholds(diff, thresholdGain, thresholdLoss, currentValue, STELLAR, stellarWatcher, whatsappMessageSender);
    }

}
