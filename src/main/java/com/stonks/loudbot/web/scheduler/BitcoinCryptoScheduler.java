package com.stonks.loudbot.web.scheduler;

import com.stonks.loudbot.model.CryptoCurrency;
import com.stonks.loudbot.web.service.MessageSenderService;
import com.stonks.loudbot.web.watcher.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class BitcoinCryptoScheduler extends CryptoScheduler {

    private static final Logger LOGGER = Logger.getLogger(BitcoinCryptoScheduler.class.getName());

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
        LOGGER.log(Level.INFO, BITCOIN.getName() + " scheduler has started.");
        if(bitcoinWatcher.getCheckpoint() == 0){
            bitcoinWatcher.updateCheckpoint(getCryptoCurrentValue(BITCOIN.getCode(), currency));
        }
    }

    @Override
    @Scheduled(fixedRateString = "${scheduler.rate}", initialDelay = 2000)
    protected void scheduleCheck() {
        double currentValue = getCryptoCurrentValue(BITCOIN.getCode(), currency);
        double diff = bitcoinWatcher.checkDiff(currentValue);
        checkDifference(diff, thresholdGain, thresholdLoss, currentValue, BITCOIN, bitcoinWatcher, whatsappMessageSender);
    }

}
