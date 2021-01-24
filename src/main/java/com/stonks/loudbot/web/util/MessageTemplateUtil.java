package com.stonks.loudbot.web.util;

import com.stonks.loudbot.model.CryptoCurrency;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageTemplateUtil {

    private MessageTemplateUtil() {
    }

    public static String formatDate(LocalDateTime localDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy hh:mm");
        return localDate.format(formatter);
    }

    public static String messageGain(CryptoCurrency cryptoCurrency, double diff, String currency, double previousValue, double currentValue, LocalDateTime lastCheckDate) {
        return String.format ("[%s] %s is up by %.2f%% since %s. %n%s %.2f -> %s %.2f",
                cryptoCurrency.getCode(),
                cryptoCurrency.getName(),
                diff,
                formatDate(lastCheckDate),
                currency, previousValue,
                currency, currentValue);
    }

    public static String messageLoss(CryptoCurrency cryptoCurrency, double diff, String currency, double previousValue, double currentValue, LocalDateTime lastCheckDate) {
        return String.format ("[%s] %s is down by %.2f%% since %s. %n%s %.2f -> %s %.2f",
                cryptoCurrency.getCode(),
                cryptoCurrency.getName(),
                diff,
                formatDate(lastCheckDate),
                currency, previousValue,
                currency, currentValue);
    }

    public static String messageDiff(CryptoCurrency cryptoCurrency, double diff, String currency, double previousValue, double currentValue) {
        return String.format ("[%s] %s diff is %.2f%% since last check. %n%s %.2f -> %s %.2f",
                cryptoCurrency.getCode(),
                cryptoCurrency.getName(),
                diff,
                currency, previousValue,
                currency, currentValue);
    }

}
