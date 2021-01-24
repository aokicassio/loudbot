package com.stonks.loudbot.web.scheduler;

import com.stonks.loudbot.model.CryptoCurrency;
import com.stonks.loudbot.web.service.MessageSenderService;
import com.stonks.loudbot.web.watcher.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BitcoinCryptoScheduler extends CryptoScheduler {

    @Value("${threshold.crypto.bitcoin.gain}")
    private double thresholdGain;

    @Value("${threshold.crypto.bitcoin.loss}")
    private double thresholdLoss;

    @Autowired
    private Watcher bitcoinWatcher;

    @Autowired
    protected MessageSenderService whatsappMessageSender;

    private static final CryptoCurrency BITCOIN = CryptoCurrency.BITCOIN;

    @PostConstruct
    public void initBitcoinCryptoScheduler(){
        initWatcher(BITCOIN, bitcoinWatcher);
    }

    @Override
    @Scheduled(fixedRateString = "${scheduler.rate}", initialDelay = 2000)
    protected void scheduleCheck() {
        double currentValue = getCryptoCurrentValue(BITCOIN.getCode(), currency);
        double diff = bitcoinWatcher.checkDiff(currentValue);
        compareWithThresholds(diff, thresholdGain, thresholdLoss, currentValue, BITCOIN, bitcoinWatcher, whatsappMessageSender);
    }

}
