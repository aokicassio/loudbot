package com.stonks.loudbot.web.service;

import reactor.core.publisher.Mono;

public interface CryptoCompareService {

    Mono<String> getCryptoCurrentPrice(String crypto, String currency);
}
