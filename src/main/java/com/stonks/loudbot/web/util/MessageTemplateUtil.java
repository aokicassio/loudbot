package com.stonks.loudbot.web.util;

import com.stonks.loudbot.model.CryptoCurrency;

public class MessageTemplateUtil {

    private MessageTemplateUtil() {
    }

    public static String messageGain(CryptoCurrency cryptoCurrency, double threshold, String currency, double previousValue, double currentValue) {
        return String.format ("[%s] %s is up by %s percent. %n%s %.2f -> %s %.2f",
                cryptoCurrency.getCode(),
                cryptoCurrency.getName(),
                threshold,
                currency, previousValue,
                currency, currentValue);
    }

    public static String messageLoss(CryptoCurrency cryptoCurrency, double threshold, String currency, double previousValue, double currentValue) {
        return String.format ("[%s] %s is down by %s percent. %n%s %.2f -> %s %.2f",
                cryptoCurrency.getCode(),
                cryptoCurrency.getName(),
                threshold,
                currency, previousValue,
                currency, currentValue);
    }

    public static String messageDiff(CryptoCurrency cryptoCurrency, double threshold, String currency, double previousValue, double currentValue) {
        return String.format ("[%s] %s diff is %.2f percent since last check. %n%s %.2f -> %s %.2f",
                cryptoCurrency.getCode(),
                cryptoCurrency.getName(),
                threshold,
                currency, previousValue,
                currency, currentValue);
    }

}
