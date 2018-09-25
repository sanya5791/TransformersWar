package com.akhutornoy.transformerswar.interactor.battle.mars;

import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RatingCalculatorTest {

    RatingCalculator calculator;

    @Before
    public void init() {
        calculator = new RatingCalculator();
    }

    @Test
    public void ratingShouldBe50() {
        int expected = 50;

        Transformer transformer = new Transformer.Builder()
                .setStrength(10)
                .setIntelligence(10)
                .setSpeed(10)
                .setEndurance(10)
                .setFirepower(10)
                .build();
        int actual = calculator.calculate(transformer);

        assertEquals(String.format("Rating should be %d", expected), expected, actual);
    }
}