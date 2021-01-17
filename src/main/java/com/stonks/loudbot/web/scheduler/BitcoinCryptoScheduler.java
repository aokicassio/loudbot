package com.stonks.loudbot.web.scheduler;

import com.stonks.loudbot.model.Crypto;
import com.stonks.loudbot.web.service.CryptoCompareService;
import com.stonks.loudbot.web.service.MessageSenderService;
import com.stonks.loudbot.web.service.impl.BitcoinWatcher;
import com.stonks.loudbot.web.util.EntityMapper;
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

    @Value("${crypto.bitcoin}")
    private String bitcoinCode;

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

        if(diff >= 10){
            bitcoinWatcher.setCheckpoint(currentValue);
            sendMessage(whatsappMessageSender, String.format ("[%s] Bitcoin is up by 10 percent. \n%s%.2f -> %s %.2f",
                    bitcoinCode,
                    currency, bitcoinWatcher.getCheckpoint(),
                    currency, currentValue));
        } else if (diff <= -10) {
            bitcoinWatcher.setCheckpoint(currentValue);
            sendMessage(whatsappMessageSender, String.format ("[%s] Bitcoin is down by 10 percent. \n%s%.2f -> %s %.2f",
                    bitcoinCode,
                    currency, bitcoinWatcher.getCheckpoint(),
                    currency, currentValue));
        } else {
            sendMessage(whatsappMessageSender, String.format ("[%s] Bitcoin diff is %.2f percent since last check. \n%s %.2f -> %s %.2f",
                    bitcoinCode, diff,
                    currency, bitcoinWatcher.getCheckpoint(),
                    currency, currentValue));
        }

    }

    public double getBitcoinCurrentValue(String code, String currency){
        Mono<String> bitcoin = cryptoCompareService.getCryptoCurrentPrice(code, currency);
        String response = bitcoin.block();
        Crypto crypto = EntityMapper.parseCryptoFromJsonString(response.toLowerCase());

        return crypto.getEur();
    }
}
