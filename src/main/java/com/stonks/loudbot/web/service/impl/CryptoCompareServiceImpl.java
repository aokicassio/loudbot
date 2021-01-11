package com.stonks.loudbot.web.service.impl;

import com.stonks.loudbot.web.service.CryptoCompareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CryptoCompareServiceImpl implements CryptoCompareService {

    @Value("${api.cryptocompare.uri}")
    private String cryptocompareUri;

    @Value("${api.cryptocompare.param.cryptotype}")
    private String paramCryptoType;

    @Value("${api.cryptocompare.param.currency}")
    private String paramCurrency;

    @Autowired
    private WebClient webClient;

    @Override
    public Mono<String> getCryptoCurrentPrice(String crypto, String currency) {

        return  webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(cryptocompareUri)
                        .queryParam(paramCryptoType, crypto)
                        .queryParam(paramCurrency, currency)
                        .build())
                .retrieve().bodyToMono(String.class);
    }

}
