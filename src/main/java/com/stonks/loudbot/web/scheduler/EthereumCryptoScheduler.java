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
public class EthereumCryptoScheduler extends CryptoScheduler {


    @Value("${threshold.crypto.ethereum.gain}")
    private double thresholdGain;

    @Value("${threshold.crypto.ethereum.loss}")
    private double thresholdLoss;

    @Autowired
    private Watcher ethereumWatcher;

    @Autowired
    protected MessageSenderService whatsappMessageSender;

    private static final CryptoCurrency ETHEREUM = CryptoCurrency.ETHEREUM;

    @PostConstruct
    public void initEthereumCryptoScheduler(){
        initWatcher(ETHEREUM, ethereumWatcher);
    }

    @Override
    @Scheduled(fixedRateString = "${scheduler.rate}", initialDelay = 2000)
    protected void scheduleCheck() {
        double currentValue = getCryptoCurrentValue(ETHEREUM.getCode(), currency);
        double diff = ethereumWatcher.checkDiff(currentValue);
        compareWithThresholds(diff, thresholdGain, thresholdLoss, currentValue, ETHEREUM, ethereumWatcher, whatsappMessageSender);
    }
}
