package com.stonks.loudbot.web.scheduler;

import com.stonks.loudbot.model.CryptoCurrency;
import com.stonks.loudbot.web.service.MessageSenderService;
import com.stonks.loudbot.web.service.impl.BitcoinWatcher;
import com.stonks.loudbot.web.util.MessageTemplateUtil;
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
    private BitcoinWatcher bitcoinWatcher;

    @Autowired
    protected MessageSenderService whatsappMessageSender;

    @PostConstruct
    public void initBitcoinCryptoScheduler(){
        if(bitcoinWatcher.getCheckpoint() == 0){
            bitcoinWatcher.updateCheckpoint(getCryptoCurrentValue(CryptoCurrency.BITCOIN.getCode(), currency));
        }
    }

    @Override
    @Scheduled(fixedRateString = "${scheduler.rate}", initialDelay = 2000)
    protected void scheduleCheck() {
        LOGGER.log(Level.INFO, "Bitcoin Scheduler check triggered");

        double currentValue = getCryptoCurrentValue(CryptoCurrency.BITCOIN.getCode(), currency);

        double diff = bitcoinWatcher.checkDiff(currentValue);

        if(diff >= thresholdGain) {
            bitcoinWatcher.setCheckpoint(currentValue);
            sendMessage(whatsappMessageSender, MessageTemplateUtil.messageGain(
                    CryptoCurrency.BITCOIN.getCode(), CryptoCurrency.BITCOIN.getName(), thresholdGain, currency, bitcoinWatcher.getCheckpoint(), currentValue));
        } else if (diff <= thresholdLoss) {
            bitcoinWatcher.setCheckpoint(currentValue);
            sendMessage(whatsappMessageSender, MessageTemplateUtil.messageLoss(
                    CryptoCurrency.BITCOIN.getCode(), CryptoCurrency.BITCOIN.getName(), thresholdLoss, currency, bitcoinWatcher.getCheckpoint(), currentValue));
        } else {
            sendMessage(whatsappMessageSender, MessageTemplateUtil.messageDiff(
                    CryptoCurrency.BITCOIN.getCode(), CryptoCurrency.BITCOIN.getName(), diff, currency, bitcoinWatcher.getCheckpoint(), currentValue));
        }

    }

}
