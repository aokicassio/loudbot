package com.stonks.loudbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CryptoCurrency {

    BITCOIN("Bitcoin", "BTC"),
    ETHEREUM("Ethereum", "ETH"),
    LITECOIN("Litecoin", "LTC"),
    STELLAR("Stellar", "XLM"),
    EOS("EOS", "EOS"),
    ZERO_X("0x", "ZRX"),
    TEZOS("Tezos", "XTZ"),
    OMG("OMG Network", "OMG");

    private final String name;
    private final String code;

}
