package com.akhutornoy.transformerswar.interactor.battle.mars

import com.akhutornoy.transformerswar.repository.cache.TransformerEntity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RatingCalculatorTest {
    internal var calculator = RatingCalculator()

    @Before
    fun init() {
        calculator = RatingCalculator()
    }

    @Test
    fun `rating Should Be 50`() {
        val expected = 50

        val transformer = TransformerEntity(
                strength = 10,
                intelligence = 10,
                speed = 10,
                endurance = 10,
                rank = 10,
                courage = 10,
                firepower = 10,
                skill = 10)
        val actual = calculator.calculate(transformer)

        assertEquals(String.format("Rating should be %d", expected), expected.toLong(), actual.toLong())
    }

}