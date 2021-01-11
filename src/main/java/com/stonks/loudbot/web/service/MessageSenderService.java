package com.stonks.loudbot.web.service;

public interface MessageSenderService {

    void sendMessage(String body, String phoneNumberTo);

}
