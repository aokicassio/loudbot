package com.stonks.loudbot.web.service.impl;

import com.stonks.loudbot.business.Calculator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class BitcoinWatcher {

    private double checkpoint;

    public double checkDiff(double newPrice){
        return Calculator.calculateDiff(checkpoint, newPrice);
    }

    public void updateCheckpoint(double newValue){
        this.setCheckpoint(newValue);
    }
}
