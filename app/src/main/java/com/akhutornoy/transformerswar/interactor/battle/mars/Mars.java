package com.akhutornoy.transformerswar.interactor.battle.mars;

import com.akhutornoy.transformerswar.repository.cache.TransformerEntity;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.ui.battle.model.AfterBattleState;
import com.akhutornoy.transformerswar.ui.battle.model.BeforeBattleState;
import com.akhutornoy.transformerswar.ui.battle.model.Fighters;
import com.akhutornoy.transformerswar.ui.battle.model.Team;

import java.util.ArrayList;
import java.util.List;

public class Mars {

    private final BattleInitializer battleInitializer;
    private final TransformersArena transformersArena;

    public Mars(BattleInitializer battleInitializer, TransformersArena transformersArena) {
        this.battleInitializer = battleInitializer;
        this.transformersArena = transformersArena;
    }

    public List<Fighters> disposeTransformers(List<TransformerEntity> transformers) {
        return battleInitializer.disposeTransformers(transformers);
    }

    public AfterBattleState startBattle(BeforeBattleState beforeBattleState) {
        List<Fighters> fightersResult = new ArrayList<>(beforeBattleState.getFacedFighters().size());
        for (Fighters fighters : beforeBattleState.getFacedFighters()) {
            fightersResult.add(transformersArena.fight(fighters));
        }
        return getAfterBattleState(fightersResult);
    }

    private AfterBattleState getAfterBattleState(List<Fighters> fightersResult) {
        int autobotKilled = 0;
        int decepticonsKilled = 0;

        for (Fighters fighters : fightersResult) {
            switch (fighters.getFightResult()) {
                case AUTOBOT_WINNER:
                    ++decepticonsKilled;
                    break;
                case DECIPTICON_WINNER:
                    ++autobotKilled;
                    break;
                case BOTH_KILLED:
                    ++autobotKilled;
                    ++decepticonsKilled;
                    break;
                case TOTAL_ANNIHILATION:
                    return new AfterBattleState(fightersResult, Team.TOTAL_ANNIHILATION, 0, 0);
            }
        }

        return new AfterBattleState(fightersResult,
                getWinnerTeam(autobotKilled, decepticonsKilled),
                autobotKilled,
                decepticonsKilled);
    }

    private Team getWinnerTeam(int autobotKilled, int decepticonsKilled) {
        if (autobotKilled < decepticonsKilled) {
            return Team.AUTOBOT;
        } else if (decepticonsKilled < autobotKilled) {
            return Team.DECEPTICON;
        } else {
            return Team.NO_TEAM;
        }
    }
}
