package com.akhutornoy.transformerswar.ui.battle.model;

import com.akhutornoy.transformerswar.repository.cache.TransformerEntity;

public class Fighters {
    private final TransformerEntity autobot;
    private final TransformerEntity decepticon;
    private final FightResult fightResult;

    public Fighters(TransformerEntity autobot, TransformerEntity decepticon, FightResult fightResult) {
        this.autobot = autobot;
        this.decepticon = decepticon;
        this.fightResult = fightResult;
    }

    public TransformerEntity getAutobot() {
        return autobot;
    }

    public TransformerEntity getDecepticon() {
        return decepticon;
    }

    public FightResult getFightResult() {
        return fightResult;
    }

    public enum FightResult {
        BOTH_ALIVE, AUTOBOT_WINNER, DECIPTICON_WINNER, BOTH_KILLED, TOTAL_ANNIHILATION
    }
}
