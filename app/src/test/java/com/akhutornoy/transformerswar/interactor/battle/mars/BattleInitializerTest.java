package com.akhutornoy.transformerswar.interactor.battle.mars;

import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.ui.battle.model.Fighters;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BattleInitializerTest {
    public BattleInitializer battleInitializer;

    @Before
    public void init() {
        battleInitializer = new BattleInitializer();
    }

    @Test
    public void emptyListTransformers_returnNoFighters() {
        int expectedFightersCount = 0;
        List<Fighters> fighters = battleInitializer.disposeTransformers(new ArrayList<>());
        int actualFightersCount = fighters.size();

        assertEquals(expectedFightersCount, actualFightersCount);
    }

    @Test
    public void twoTransformersWithinSameTeam_returnNoFighters() {
        int expectedFightersCount = 0;
        Transformer a1 = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .build();
        Transformer a2 = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .build();
        List<Transformer> transformers = new ArrayList<>();
        transformers.add(a1);
        transformers.add(a2);

        List<Fighters> fighters = battleInitializer.disposeTransformers(transformers);
        int actualFightersCount = fighters.size();

        assertEquals(expectedFightersCount, actualFightersCount);
    }

    @Test
    public void twoDifferentTeamTransformersWithDiffRank_returnNoFighters() {
        int expectedFightersCount = 0;
        Transformer a = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .setRank(1)
                .build();
        Transformer d = new Transformer.Builder()
                .setTeam(Transformer.DECEPTICON_TEAM)
                .setRank(2)
                .build();
        List<Transformer> transformers = new ArrayList<>();
        transformers.add(a);
        transformers.add(d);

        List<Fighters> fighters = battleInitializer.disposeTransformers(transformers);
        int actualFightersCount = fighters.size();

        assertEquals(expectedFightersCount, actualFightersCount);
    }

    @Test
    public void oneAutobotAndOneDecipt_returnOneFighters() {
        int expectedFightersCount = 1;

        Transformer a = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .setRank(1)
                .build();
        Transformer d = new Transformer.Builder()
                .setTeam(Transformer.DECEPTICON_TEAM)
                .setRank(1)
                .build();
        List<Transformer> transformers = new ArrayList<>();
        transformers.add(a);
        transformers.add(d);

        List<Fighters> fighters = battleInitializer.disposeTransformers(transformers);
        int actualFightersCount = fighters.size();

        assertEquals(expectedFightersCount, actualFightersCount);
    }

    @Test
    public void twoAutobotAndOneDecipt_returnOneFighters() {
        int expectedFightersCount = 1;

        Transformer a1 = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .setRank(1)
                .build();
        Transformer a2 = new Transformer.Builder()
                .setTeam(Transformer.AUTOBOT_TEAM)
                .setRank(1)
                .build();
        Transformer d = new Transformer.Builder()
                .setTeam(Transformer.DECEPTICON_TEAM)
                .setRank(1)
                .build();
        List<Transformer> transformers = new ArrayList<>();
        transformers.add(a1);
        transformers.add(a2);
        transformers.add(d);

        List<Fighters> fighters = battleInitializer.disposeTransformers(transformers);
        int actualFightersCount = fighters.size();

        assertEquals(expectedFightersCount, actualFightersCount);
    }
}