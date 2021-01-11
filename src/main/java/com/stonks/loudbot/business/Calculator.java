package com.stonks.loudbot.business;

public class Calculator {

    public static double calculateDiff(double initialValue, double finalValue){
        return ((initialValue - finalValue) / initialValue) * -100;
    }

    public static void main(String[] args) {
        System.out.println(calculateDiff(25, 50));
    }
}
