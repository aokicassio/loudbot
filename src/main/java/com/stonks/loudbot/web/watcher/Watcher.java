package com.stonks.loudbot.web.watcher;

import com.stonks.loudbot.business.Calculator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Watcher {

    private double checkpoint;

    public double checkDiff(double newPrice){
        return Calculator.calculateDiff(checkpoint, newPrice);
    }

    public void updateCheckpoint(double newValue){
        this.setCheckpoint(newValue);
    }
}
