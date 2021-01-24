package com.stonks.loudbot.web.scheduler;

import com.stonks.loudbot.model.CryptoCurrency;
import com.stonks.loudbot.web.service.MessageSenderService;
import com.stonks.loudbot.web.watcher.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ZeroXCryptoScheduler extends CryptoScheduler {

    @Value("${threshold.crypto.stellar.gain}")
    private double thresholdGain;

    @Value("${threshold.crypto.stellar.loss}")
    private double thresholdLoss;

    @Autowired
    private Watcher zeroXWatcher;

    @Autowired
    protected MessageSenderService whatsappMessageSender;

    public static final CryptoCurrency ZERO_X = CryptoCurrency.ZERO_X;

    @PostConstruct
    public void initZeroXCryptoScheduler(){
        initWatcher(ZERO_X, zeroXWatcher);
    }

    @Override
    protected void scheduleCheck() {
        double currentValue = getCryptoCurrentValue(ZERO_X.getCode(), currency);
        double diff = zeroXWatcher.checkDiff(currentValue);
        compareWithThresholds(diff, thresholdGain, thresholdLoss, currentValue, ZERO_X, zeroXWatcher, whatsappMessageSender);
    }

}
