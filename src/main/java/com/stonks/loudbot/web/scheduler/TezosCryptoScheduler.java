package com.stonks.loudbot.web.scheduler;

import com.stonks.loudbot.model.CryptoCurrency;
import com.stonks.loudbot.web.service.MessageSenderService;
import com.stonks.loudbot.web.watcher.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TezosCryptoScheduler extends CryptoScheduler {

    @Value("${threshold.crypto.stellar.gain}")
    private double thresholdGain;

    @Value("${threshold.crypto.stellar.loss}")
    private double thresholdLoss;

    @Autowired
    private Watcher tezosWatcher;

    @Autowired
    protected MessageSenderService whatsappMessageSender;

    public static final CryptoCurrency TEZOS = CryptoCurrency.TEZOS;

    @PostConstruct
    public void initTezosCryptoScheduler(){
        initWatcher(TEZOS, tezosWatcher);
    }

    @Override
    protected void scheduleCheck() {
        double currentValue = getCryptoCurrentValue(TEZOS.getCode(), currency);
        double diff = tezosWatcher.checkDiff(currentValue);
        compareWithThresholds(diff, thresholdGain, thresholdLoss, currentValue, TEZOS, tezosWatcher, whatsappMessageSender);
    }

}
