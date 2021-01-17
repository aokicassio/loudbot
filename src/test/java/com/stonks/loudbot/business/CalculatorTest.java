package com.stonks.loudbot.business;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculatorTest {

    @Test
    void testCalculateDiffWhenGain(){
        double result = Calculator.calculateDiff(1000, 1200);
        Assertions.assertThat(result).isEqualTo(20);
    }

    @Test
    void testCalculateDiffWhenLoss(){
        double result = Calculator.calculateDiff(1000, 800);
        Assertions.assertThat(result).isEqualTo(-20);
    }

    @Test
    void testCalculateDiffWhenZero(){
        double result = Calculator.calculateDiff(1, 1);
        Assertions.assertThat(result).isZero();
    }

}
