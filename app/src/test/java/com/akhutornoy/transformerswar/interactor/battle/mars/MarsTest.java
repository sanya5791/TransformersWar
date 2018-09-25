package com.akhutornoy.transformerswar.interactor.battle.mars;

import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.ui.battle.model.AfterBattleState;
import com.akhutornoy.transformerswar.ui.battle.model.BeforeBattleState;
import com.akhutornoy.transformerswar.ui.battle.model.Fighters;
import com.akhutornoy.transformerswar.ui.battle.model.Team;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MarsTest {
    private static final String OPTIMUS_PRIME = "Optimus Prime";
    private static final String PREDAKING = "Predaking";

    private Mars mars;

    @Before
    public void init() {
        BattleInitializer battleInitializer = new BattleInitializer();
        RatingCalculator calculator = new RatingCalculator();
        TransformersArena transformersArena = new TransformersArena(calculator);
        mars = new Mars(battleInitializer, transformersArena);
    }

    @Test
    public void emptyList_returnNoTeamWinnerResult() {
        Team expected = Team.NO_TEAM;

        List<Fighters> fighters = new ArrayList<>();
        BeforeBattleState beforeBattleState = new BeforeBattleState(fighters);
        AfterBattleState afterBattleState = mars.startBattle(beforeBattleState);

        Team actual = afterBattleState.getWinner();
        assertEquals(expected, actual);
    }

    @Test
    public void returnAutobotWins() {
        Team expected = Team.AUTOBOT;
        Transformer a = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .setStrength(2)
                .build();
        Transformer d = new Transformer.Builder()
                .setTeam(Transformer.DECEPTICON_TEAM)
                .setStrength(1)
                .build();
        List<Fighters> fighters = new ArrayList<>();
        fighters.add(new Fighters(a, d, Fighters.FightResult.BOTH_ALIVE));
        BeforeBattleState beforeBattleState = new BeforeBattleState(fighters);
        AfterBattleState afterBattleState = mars.startBattle(beforeBattleState);

        Team actual = afterBattleState.getWinner();
        assertEquals(expected, actual);
    }

    @Test
    public void returnDecepticonWinsAndAutotKilled() {
        Team expectedTeam = Team.DECEPTICON;
        int expectedAutobotsKilled = 1;
        int expectedDecepticonssKilled = 0;

        Transformer a = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .setStrength(1)
                .build();
        Transformer d = new Transformer.Builder()
                .setTeam(Transformer.DECEPTICON_TEAM)
                .setStrength(2)
                .build();
        List<Fighters> fighters = new ArrayList<>();
        fighters.add(new Fighters(a, d, Fighters.FightResult.BOTH_ALIVE));
        BeforeBattleState beforeBattleState = new BeforeBattleState(fighters);
        AfterBattleState afterBattleState = mars.startBattle(beforeBattleState);

        //who win battle
        Team actual = afterBattleState.getWinner();
        assertEquals(expectedTeam, actual);

        //check Decipticons killed number
        int actualDecipticonsKilled = afterBattleState.getDecepticonsKilled();
        assertEquals(expectedDecepticonssKilled, actualDecipticonsKilled);

        //check Autobot killed number
        int actualAutobotKilled = afterBattleState.getAutobotsKilled();
        assertEquals(expectedAutobotsKilled, actualAutobotKilled);

    }

    @Test
    public void returnAutobotWinsAndDecipticonsKilled() {
        Team expectedTeam = Team.AUTOBOT;
        int expectedAutobotsKilled = 0;
        int expectedDecepticonssKilled = 1;

        Transformer a = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .setStrength(2)
                .build();
        Transformer d = new Transformer.Builder()
                .setTeam(Transformer.DECEPTICON_TEAM)
                .setStrength(1)
                .build();
        List<Fighters> fighters = new ArrayList<>();
        fighters.add(new Fighters(a, d, Fighters.FightResult.BOTH_ALIVE));
        BeforeBattleState beforeBattleState = new BeforeBattleState(fighters);
        AfterBattleState afterBattleState = mars.startBattle(beforeBattleState);

        //who wins battle
        Team actual = afterBattleState.getWinner();
        assertEquals(expectedTeam, actual);

        //check Decipticons killed number
        int actualDecipticonsKilled = afterBattleState.getDecepticonsKilled();
        assertEquals(expectedDecepticonssKilled, actualDecipticonsKilled);

        //check Autobot killed number
        int actualAutobotKilled = afterBattleState.getAutobotsKilled();
        assertEquals(expectedAutobotsKilled, actualAutobotKilled);
    }

    @Test
    public void returnTotalAnnihilation() {
        Team expectedTeam = Team.TOTAL_ANNIHILATION;

        Transformer a = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .setName(OPTIMUS_PRIME)
                .build();
        Transformer d = new Transformer.Builder()
                .setTeam(Transformer.DECEPTICON_TEAM)
                .setName(OPTIMUS_PRIME)
                .build();
        List<Fighters> fighters = new ArrayList<>();
        fighters.add(new Fighters(a, d, Fighters.FightResult.BOTH_ALIVE));
        BeforeBattleState beforeBattleState = new BeforeBattleState(fighters);
        AfterBattleState afterBattleState = mars.startBattle(beforeBattleState);

        Team actual = afterBattleState.getWinner();
        assertEquals(expectedTeam, actual);
    }
}