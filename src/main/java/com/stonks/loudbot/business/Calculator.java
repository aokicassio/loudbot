package com.stonks.loudbot.business;

public class Calculator {

    private Calculator(){
    }

    public static double calculateDiff(double initialValue, double finalValue){
        return ((initialValue - finalValue) / initialValue) * -100;
    }

}
