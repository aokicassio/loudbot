package com.stonks.loudbot.web.util;


import com.stonks.loudbot.model.CryptoCurrency;
import com.stonks.loudbot.model.Currency;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class MessageTemplateUtilTest {

    private static final CryptoCurrency ETHEREUM = CryptoCurrency.ETHEREUM;

    private LocalDateTime DATETIME = LocalDateTime.of(2021, 01, 22, 12,30);

    @Test
    void testMessageGain(){
        LocalDateTime localDateTime = LocalDateTime.of(2021, 01, 22, 12,30);
        String message = MessageTemplateUtil.messageGain(
                ETHEREUM,
                10,
                Currency.EURO.getCode(),
                1000,
                1100,
                DATETIME);

        Assertions.assertThat(message)
                .contains("[ETH] Ethereum is up by 10.0% since 22 January 2021 12:30.")
                .contains("EUR 1000.00 -> EUR 1100.00");

        System.out.println(message);
    }

    @Test
    void testMessageLoss(){
        String message = MessageTemplateUtil.messageLoss(
                ETHEREUM,
                10,
                Currency.EURO.getCode(),
                1000,
                900,
                DATETIME);

        Assertions.assertThat(message)
                .contains("[ETH] Ethereum is down by 10.0% since 22 January 2021 12:30.")
                .contains("EUR 1000.00 -> EUR 900.00");
    }

}
