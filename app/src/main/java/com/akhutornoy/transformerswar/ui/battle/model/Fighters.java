package com.akhutornoy.transformerswar.ui.battle.model;

import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;

public class Fighters {
    private final Transformer autobot;
    private final Transformer decepticon;
    private final FightResult fightResult;

    public Fighters(Transformer autobot, Transformer decepticon, FightResult fightResult) {
        this.autobot = autobot;
        this.decepticon = decepticon;
        this.fightResult = fightResult;
    }

    public Transformer getAutobot() {
        return autobot;
    }

    public Transformer getDecepticon() {
        return decepticon;
    }

    public FightResult getFightResult() {
        return fightResult;
    }

    public enum FightResult {
        BOTH_ALIVE, AUTOBOT_WINNER, DECIPTICON_WINNER, BOTH_KILLED, TOTAL_ANNIHILATION
    }
}
