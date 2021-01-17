package com.stonks.loudbot.web.scheduler;

import com.stonks.loudbot.model.Crypto;
import com.stonks.loudbot.web.service.CryptoCompareService;
import com.stonks.loudbot.web.service.MessageSenderService;
import com.stonks.loudbot.web.service.impl.BitcoinWatcher;
import com.stonks.loudbot.web.util.EntityMapper;
import com.stonks.loudbot.web.util.MessageTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class BitcoinCryptoScheduler extends CryptoScheduler {

    private static final Logger LOGGER = Logger.getLogger(BitcoinCryptoScheduler.class.getName());

    private static final String BITCOIN = "Bitcoin";

    @Value("${crypto.bitcoin}")
    private String bitcoinCode;

    @Value("${threshold.crypto.bitcoin.gain}")
    private double thresholdGain;

    @Value("${threshold.crypto.bitcoin.loss}")
    private double thresholdLoss;

    @Autowired
    private BitcoinWatcher bitcoinWatcher;

    @Autowired
    protected MessageSenderService whatsappMessageSender;

    @Autowired
    protected CryptoCompareService cryptoCompareService;

    @PostConstruct
    public void initBitcoinCryptoScheduler(){
        if(bitcoinWatcher.getCheckpoint() == 0){
            bitcoinWatcher.updateCheckpoint(getBitcoinCurrentValue(bitcoinCode, currency));
        }
    }

    @Override
    @Scheduled(fixedRateString = "${scheduler.rate}", initialDelay = 2000)
    protected void scheduleCheck() {
        LOGGER.log(Level.INFO, "Bitcoin Scheduler check triggered");

        double currentValue = getBitcoinCurrentValue(bitcoinCode, currency);

        double diff = bitcoinWatcher.checkDiff(currentValue);

        if(diff >= thresholdGain) {
            bitcoinWatcher.setCheckpoint(currentValue);
            sendMessage(whatsappMessageSender, MessageTemplateUtil.messageGain(bitcoinCode, BITCOIN, thresholdGain, currency, bitcoinWatcher.getCheckpoint(), currentValue));
        } else if (diff <= thresholdLoss) {
            bitcoinWatcher.setCheckpoint(currentValue);
            sendMessage(whatsappMessageSender, MessageTemplateUtil.messageLoss(bitcoinCode, BITCOIN, thresholdLoss, currency, bitcoinWatcher.getCheckpoint(), currentValue));
        } else {
            sendMessage(whatsappMessageSender, MessageTemplateUtil.messageDiff(bitcoinCode, BITCOIN, diff, currency, bitcoinWatcher.getCheckpoint(), currentValue));
        }

    }

    public double getBitcoinCurrentValue(String code, String currency){
        Mono<String> bitcoin = cryptoCompareService.getCryptoCurrentPrice(code, currency);
        String response = bitcoin.block();
        Crypto crypto = EntityMapper.parseCryptoFromJsonString(response.toLowerCase());

        return crypto.getEur();
    }
}
