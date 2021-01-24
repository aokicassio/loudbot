package com.stonks.loudbot.web.watcher;

import com.stonks.loudbot.business.Calculator;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Watcher {

    private double checkpoint;
    private LocalDateTime lastCheckpointDate;

    public double checkDiff(double newPrice){
        return Calculator.calculateDiff(checkpoint, newPrice);
    }

    public void updateCheckpoint(double newValue){
        this.setCheckpoint(newValue);
    }
}
