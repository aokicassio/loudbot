package com.stonks.loudbot.web.util;

public class MessageTemplateUtil {

    private MessageTemplateUtil() {
    }

    public static String messageGain(String cryptoCode, String cryptoName, double threshold, String currency, double previousValue, double currentValue) {
        return String.format ("[%s] %s is up by %s percent. %n%s%.2f -> %s %.2f",
                cryptoCode,
                cryptoName,
                threshold,
                currency, previousValue,
                currency, currentValue);
    }

    public static String messageLoss(String cryptoCode, String cryptoName, double threshold, String currency, double previousValue, double currentValue) {
        return String.format ("[%s] %s is down by %s percent. %n%s%.2f -> %s %.2f",
                cryptoCode,
                cryptoName,
                threshold,
                currency, previousValue,
                currency, currentValue);
    }

    public static String messageDiff(String cryptoCode, String cryptoName, double threshold, String currency, double previousValue, double currentValue) {
        return String.format ("[%s] %s diff is %.2f percent since last check. %n%s %.2f -> %s %.2f",
                cryptoCode,
                cryptoName,
                threshold,
                currency, previousValue,
                currency, currentValue);
    }

}
