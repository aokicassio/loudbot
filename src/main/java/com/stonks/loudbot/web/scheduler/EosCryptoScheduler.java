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
public class EosCryptoScheduler extends CryptoScheduler {

    private static final Logger LOGGER = Logger.getLogger(EosCryptoScheduler.class.getName());

    @Value("${threshold.crypto.stellar.gain}")
    private double thresholdGain;

    @Value("${threshold.crypto.stellar.loss}")
    private double thresholdLoss;

    @Autowired
    private Watcher eosWatcher;

    @Autowired
    protected MessageSenderService whatsappMessageSender;

    public static final CryptoCurrency EOS = CryptoCurrency.EOS;

    @PostConstruct
    public void initEosCryptoScheduler(){
        LOGGER.log(Level.INFO, EOS.getName() + " scheduler has started.");
        if(eosWatcher.getCheckpoint() == 0){
            eosWatcher.updateCheckpoint(getCryptoCurrentValue(EOS.getCode(), currency));
        }
    }

    @Override
    protected void scheduleCheck() {
        double currentValue = getCryptoCurrentValue(EOS.getCode(), currency);
        double diff = eosWatcher.checkDiff(currentValue);
        checkDifference(diff, thresholdGain, thresholdLoss, currentValue, EOS, eosWatcher, whatsappMessageSender);
    }

}
