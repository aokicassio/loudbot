package com.stonks.loudbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Currency {

    EURO("Euro", "EUR"),
    BRITISH_STERLING_POUND("British Sterling Pound", "GBP"),
    US_DOLLAR("American Dollar", "USD");

    private final String name;
    private final String code;

}
