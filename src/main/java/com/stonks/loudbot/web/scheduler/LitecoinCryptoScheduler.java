package com.stonks.loudbot.web.scheduler;

import com.stonks.loudbot.model.CryptoCurrency;
import com.stonks.loudbot.web.service.MessageSenderService;
import com.stonks.loudbot.web.watcher.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LitecoinCryptoScheduler extends CryptoScheduler {

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
        initWatcher(LITECOIN, litecoinWatcher);
    }

    @Override
    protected void scheduleCheck() {
        double currentValue = getCryptoCurrentValue(LITECOIN.getCode(), currency);
        double diff = litecoinWatcher.checkDiff(currentValue);
        compareWithThresholds(diff, thresholdGain, thresholdLoss, currentValue, LITECOIN, litecoinWatcher, whatsappMessageSender);
    }

}
