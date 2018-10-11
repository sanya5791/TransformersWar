package com.akhutornoy.transformerswar.interactor.battle.mars

import com.akhutornoy.transformerswar.repository.cache.TransformerEntity
import com.akhutornoy.transformerswar.ui.battle.model.Fighters
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TransformersArenaTest {
    private val OPTIMUS_PRIME = "Optimus Prime"
    private val PREDAKING = "Predaking"

    private lateinit var arena: TransformersArena

    @Before
    fun init() {
        arena = TransformersArena(RatingCalculator())
    }

    @Test
    fun returnTotalAnnihilation() {
        val expected = Fighters.FightResult.TOTAL_ANNIHILATION

        val a = TransformerEntity(name = OPTIMUS_PRIME)
        val d = TransformerEntity(name = PREDAKING)
        val fighters = Fighters(a, d, Fighters.FightResult.BOTH_ALIVE)
        val fightersResult = arena.fight(fighters)

        val actual = fightersResult.fightResult

        assertEquals(expected, actual)
    }

    @Test fun `return TotalAnnihilation Because Of Double Optimus`() {
        val expected = Fighters.FightResult.TOTAL_ANNIHILATION

        val a = TransformerEntity(name = OPTIMUS_PRIME)
        val d = TransformerEntity(name = OPTIMUS_PRIME)
        val fighters = Fighters(a, d, Fighters.FightResult.BOTH_ALIVE)
        val fightersResult = arena.fight(fighters)

        val actual = fightersResult.fightResult

        assertEquals(expected, actual)
    }

    @Test
    fun `return TotalAnnihilation Because Of Double Predaking`() {
        val expected = Fighters.FightResult.TOTAL_ANNIHILATION

        val a = TransformerEntity(name = PREDAKING)
        val d = TransformerEntity(name = PREDAKING)
        val fighters = Fighters(a, d, Fighters.FightResult.BOTH_ALIVE)
        val fightersResult = arena.fight(fighters)

        val actual = fightersResult.fightResult

        assertEquals(expected, actual)
    }

    @Test fun `return Decepticon Wins as SuperTransformer`() {
        val expected = Fighters.FightResult.DECIPTICON_WINNER

        val a = TransformerEntity(team = TransformerEntity.AUTOBOT_TEAM, name = "Ann")
        val d = TransformerEntity(team = TransformerEntity.DECEPTICON_TEAM, name = PREDAKING)
        val fighters = Fighters(a, d, Fighters.FightResult.BOTH_ALIVE)
        val fightersResult = arena.fight(fighters)

        val actual = fightersResult.fightResult

        assertEquals(expected, actual)
    }

    @Test fun `return Autobot Wins As SuperTransformer`() {
        val expected = Fighters.FightResult.AUTOBOT_WINNER

        val a = TransformerEntity(team = TransformerEntity.AUTOBOT_TEAM, name = OPTIMUS_PRIME)
        val d = TransformerEntity(team = TransformerEntity.DECEPTICON_TEAM, name = "Denny")
        val fighters = Fighters(a, d, Fighters.FightResult.BOTH_ALIVE)
        val fightersResult = arena.fight(fighters)

        val actual = fightersResult.fightResult

        assertEquals(expected, actual)
    }

    @Test fun `return Decepticon Wins By Courage And Strength Great Advance`() {
        val expected = Fighters.FightResult.DECIPTICON_WINNER

        val a = TransformerEntity(team = TransformerEntity.AUTOBOT_TEAM,
                courage = 1, strength = 1, intelligence = 1)
        val d = TransformerEntity(team = TransformerEntity.DECEPTICON_TEAM,
                courage = 5, strength = 4)
        val fighters = Fighters(a, d, Fighters.FightResult.BOTH_ALIVE)
        val fightersResult = arena.fight(fighters)

        val actual = fightersResult.fightResult

        assertEquals(expected, actual)
    }

    @Test fun `return Autobot Wins By Courage And Strength Great Advance`() {
        val expected = Fighters.FightResult.AUTOBOT_WINNER

        val a = TransformerEntity(team = TransformerEntity.AUTOBOT_TEAM,
                courage = 5, strength = 4)
        val d = TransformerEntity(team = TransformerEntity.DECEPTICON_TEAM,
                courage = 1, strength = 1,
                //set high value to be sure that it will NOT impact result
                intelligence = 100)
        val fighters = Fighters(a, d, Fighters.FightResult.BOTH_ALIVE)
        val fightersResult = arena.fight(fighters)

        val actual = fightersResult.fightResult

        assertEquals(expected, actual)
    }

    @Test fun `return Decepticon Wins By Skill Great Advance`() {
        val expected = Fighters.FightResult.DECIPTICON_WINNER

        val a = TransformerEntity(team = TransformerEntity.AUTOBOT_TEAM,
                skill = 1,
                //set high value to be sure that it will NOT impact result
                intelligence = 100)
        val d = TransformerEntity(team = TransformerEntity.DECEPTICON_TEAM,
                skill = 4)
        val fighters = Fighters(a, d, Fighters.FightResult.BOTH_ALIVE)
        val fightersResult = arena.fight(fighters)

        val actual = fightersResult.fightResult

        assertEquals(expected, actual)
    }

    @Test fun `return Autobot Wins By Skill Great Advance`() {
        val expected = Fighters.FightResult.AUTOBOT_WINNER

        val a = TransformerEntity(team = TransformerEntity.AUTOBOT_TEAM,
                skill = 4)
        val d = TransformerEntity(team = TransformerEntity.DECEPTICON_TEAM,
                skill = 1,
                //set high value to be sure that it will NOT impact result
                intelligence = 100)
        val fighters = Fighters(a, d, Fighters.FightResult.BOTH_ALIVE)
        val fightersResult = arena.fight(fighters)

        val actual = fightersResult.fightResult

        assertEquals(expected, actual)
    }

    @Test fun `return Decepticon Wins By Overall Rating`() {
        val expected = Fighters.FightResult.DECIPTICON_WINNER

        val a = TransformerEntity(team = TransformerEntity.AUTOBOT_TEAM,
                strength = 1, intelligence = 1, speed = 1, endurance = 1, firepower = 1,
                //set high value to be sure that it will NOT impact result
                courage = 100)
        val d = TransformerEntity(team = TransformerEntity.DECEPTICON_TEAM,
                strength = 2, intelligence = 2, speed = 2, endurance = 2, firepower = 2)
        val fighters = Fighters(a, d, Fighters.FightResult.BOTH_ALIVE)
        val fightersResult = arena.fight(fighters)

        val actual = fightersResult.fightResult

        assertEquals(expected, actual)
    }

    @Test fun `return Autobot Wins By Overall Rating`() {
        val expected = Fighters.FightResult.AUTOBOT_WINNER

        val a = TransformerEntity(team = TransformerEntity.AUTOBOT_TEAM,
                strength = 2, intelligence = 2, speed = 2, endurance = 2, firepower = 2)
        val d = TransformerEntity(team = TransformerEntity.DECEPTICON_TEAM,
                strength = 1, intelligence = 1, speed = 1, endurance = 1, firepower = 1,
                //set high value to be sure that it will NOT impact result
                courage = 100)
        val fighters = Fighters(a, d, Fighters.FightResult.BOTH_ALIVE)
        val fightersResult = arena.fight(fighters)

        val actual = fightersResult.fightResult

        assertEquals(expected, actual)
    }

    @Test fun `fight Equal return AllKilled`() {
        val expected = Fighters.FightResult.BOTH_KILLED

        val a = TransformerEntity(team = TransformerEntity.AUTOBOT_TEAM,
                strength = 2, intelligence = 2, speed = 2, endurance = 2, firepower = 2)
        val d = TransformerEntity(team = TransformerEntity.DECEPTICON_TEAM,
                strength = 2, intelligence = 2, speed = 2, endurance = 2, firepower = 2)
        val fighters = Fighters(a, d, Fighters.FightResult.BOTH_ALIVE)
        val fightersResult = arena.fight(fighters)

        val actual = fightersResult.fightResult

        assertEquals(expected, actual)
    }
}