package com.akhutornoy.transformerswar.interactor.battle.mars;

import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.ui.battle.model.Fighters;
import com.akhutornoy.transformerswar.ui.battle.model.Fighters.FightResult;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransformersArenaTest {
    private static final String OPTIMUS_PRIME = "Optimus Prime";
    private static final String PREDAKING = "Predaking";

    private TransformersArena arena;

    @Before
    public void init() {
        arena = new TransformersArena(new RatingCalculator());
    }

    @Test
    public void returnTotalAnnihilation() {
        FightResult expected = FightResult.TOTAL_ANNIHILATION;

        Transformer a = new Transformer.Builder()
                .setName(OPTIMUS_PRIME)
                .build();
        Transformer d = new Transformer.Builder()
                .setName(PREDAKING)
                .build();
        Fighters fighters = new Fighters(a, d, FightResult.BOTH_ALIVE);
        Fighters fightersResult = arena.fight(fighters);

        FightResult actual = fightersResult.getFightResult();

        assertEquals(expected, actual);
    }

    @Test
    public void returnTotalAnnihilationBecauseOfDoubleOptimus() {
        FightResult expected = FightResult.TOTAL_ANNIHILATION;

        Transformer a = new Transformer.Builder()
                .setName(OPTIMUS_PRIME)
                .build();
        Transformer d = new Transformer.Builder()
                .setName(OPTIMUS_PRIME)
                .build();
        Fighters fighters = new Fighters(a, d, FightResult.BOTH_ALIVE);
        Fighters fightersResult = arena.fight(fighters);

        FightResult actual = fightersResult.getFightResult();

        assertEquals(expected, actual);

    }

    @Test
    public void returnTotalAnnihilationBecauseOfDoublePredaking() {
        FightResult expected = FightResult.TOTAL_ANNIHILATION;

        Transformer a = new Transformer.Builder()
                .setName(PREDAKING)
                .build();
        Transformer d = new Transformer.Builder()
                .setName(PREDAKING)
                .build();
        Fighters fighters = new Fighters(a, d, FightResult.BOTH_ALIVE);
        Fighters fightersResult = arena.fight(fighters);

        FightResult actual = fightersResult.getFightResult();

        assertEquals(expected, actual);
    }

    @Test
    public void returnDecepticonWinsAsSuperTransformer() {
        FightResult expected = FightResult.DECIPTICON_WINNER;

        Transformer a = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .setName("Ann")
                .build();
        Transformer d = new Transformer.Builder()
                .setTeam(Transformer.DECEPTICON_TEAM)
                .setName(PREDAKING)
                .build();
        Fighters fighters = new Fighters(a, d, FightResult.BOTH_ALIVE);
        Fighters fightersResult = arena.fight(fighters);

        FightResult actual = fightersResult.getFightResult();

        assertEquals(expected, actual);
    }

    @Test
    public void returnAutobotWinsAsSuperTransformer() {
        FightResult expected = FightResult.AUTOBOT_WINNER;

        Transformer a = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .setName(OPTIMUS_PRIME)
                .build();
        Transformer d = new Transformer.Builder()
                .setTeam(Transformer.DECEPTICON_TEAM)
                .setName("Denny")
                .build();
        Fighters fighters = new Fighters(a, d, FightResult.BOTH_ALIVE);
        Fighters fightersResult = arena.fight(fighters);

        FightResult actual = fightersResult.getFightResult();

        assertEquals(expected, actual);
    }

    @Test
    public void returnDecepticonWinsByCourageAndStrengthGreatAdvance() {
        FightResult expected = FightResult.DECIPTICON_WINNER;

        Transformer a = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .setCourage(1)
                .setStrength(1)
                //set high value to be sure that it will NOT impact result
                .setIntelligence(100)
                .build();
        Transformer d = new Transformer.Builder()
                .setTeam(Transformer.DECEPTICON_TEAM)
                .setCourage(5)
                .setStrength(4)
                .build();
        Fighters fighters = new Fighters(a, d, FightResult.BOTH_ALIVE);
        Fighters fightersResult = arena.fight(fighters);

        FightResult actual = fightersResult.getFightResult();

        assertEquals(expected, actual);
    }

    @Test
    public void returnAutobotWinsByCourageAndStrengthGreatAdvance() {
        FightResult expected = FightResult.AUTOBOT_WINNER;

        Transformer a = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .setCourage(5)
                .setStrength(4)
                .build();
        Transformer d = new Transformer.Builder()
                .setTeam(Transformer.DECEPTICON_TEAM)
                .setCourage(1)
                .setStrength(1)
                //set high value to be sure that it will NOT impact result
                .setIntelligence(100)
                .build();
        Fighters fighters = new Fighters(a, d, FightResult.BOTH_ALIVE);
        Fighters fightersResult = arena.fight(fighters);

        FightResult actual = fightersResult.getFightResult();

        assertEquals(expected, actual);
    }

    @Test
    public void returnDecepticonWinsBySkillGreatAdvance() {
        FightResult expected = FightResult.DECIPTICON_WINNER;

        Transformer a = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .setSkill(1)
                //set high value to be sure that it will NOT impact result
                .setIntelligence(100)
                .build();
        Transformer d = new Transformer.Builder()
                .setTeam(Transformer.DECEPTICON_TEAM)
                .setSkill(4)
                .build();
        Fighters fighters = new Fighters(a, d, FightResult.BOTH_ALIVE);
        Fighters fightersResult = arena.fight(fighters);

        FightResult actual = fightersResult.getFightResult();

        assertEquals(expected, actual);
    }

    @Test
    public void returnAutobotWinsBySkillGreatAdvance() {
        FightResult expected = FightResult.AUTOBOT_WINNER;

        Transformer a = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .setSkill(4)
                .build();
        Transformer d = new Transformer.Builder()
                .setTeam(Transformer.DECEPTICON_TEAM)
                .setSkill(1)
                //set high value to be sure that it will NOT impact result
                .setIntelligence(100)
                .build();
        Fighters fighters = new Fighters(a, d, FightResult.BOTH_ALIVE);
        Fighters fightersResult = arena.fight(fighters);

        FightResult actual = fightersResult.getFightResult();

        assertEquals(expected, actual);
    }
    @Test
    public void returnDecepticonWinsByOverallRating() {
        FightResult expected = FightResult.DECIPTICON_WINNER;

        Transformer a = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .setStrength(1)
                .setIntelligence(1)
                .setSpeed(1)
                .setEndurance(1)
                .setFirepower(1)
                //set high value to be sure that it will NOT impact result
                .setCourage(100)
                .build();

        Transformer d = new Transformer.Builder()
                .setTeam(Transformer.DECEPTICON_TEAM)
                .setStrength(2)
                .setIntelligence(2)
                .setSpeed(2)
                .setEndurance(2)
                .setFirepower(2)
                .build();
        Fighters fighters = new Fighters(a, d, FightResult.BOTH_ALIVE);
        Fighters fightersResult = arena.fight(fighters);

        FightResult actual = fightersResult.getFightResult();

        assertEquals(expected, actual);
    }

    @Test
    public void returnAutobotWinsByOverallRating() {
        FightResult expected = FightResult.AUTOBOT_WINNER;

        Transformer a = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .setStrength(2)
                .setIntelligence(2)
                .setSpeed(2)
                .setEndurance(2)
                .setFirepower(2)
                .build();
        Transformer d = new Transformer.Builder()
                .setTeam(Transformer.DECEPTICON_TEAM)
                .setStrength(1)
                .setIntelligence(1)
                .setSpeed(1)
                .setEndurance(1)
                .setFirepower(1)
                //set high value to be sure that it will NOT impact result
                .setCourage(100)
                .build();
        Fighters fighters = new Fighters(a, d, FightResult.BOTH_ALIVE);
        Fighters fightersResult = arena.fight(fighters);

        FightResult actual = fightersResult.getFightResult();

        assertEquals(expected, actual);
    }

    @Test
    public void fightEqual_returnAllKilled() {
        FightResult expected = FightResult.BOTH_KILLED;

        Transformer a = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .setStrength(2)
                .setIntelligence(2)
                .setSpeed(2)
                .setEndurance(2)
                .setFirepower(2)
                .build();
        Transformer d = new Transformer.Builder()
                .setTeam(Transformer.DECEPTICON_TEAM)
                .setStrength(2)
                .setIntelligence(2)
                .setSpeed(2)
                .setEndurance(2)
                .setFirepower(2)
                .build();
        Fighters fighters = new Fighters(a, d, FightResult.BOTH_ALIVE);
        Fighters fightersResult = arena.fight(fighters);

        FightResult actual = fightersResult.getFightResult();

        assertEquals(expected, actual);
    }
}