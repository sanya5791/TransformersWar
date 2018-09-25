package com.akhutornoy.transformerswar.ui.battle.model;

import java.util.List;

public class AfterBattleState {
    private final List<Fighters> facedFighters;
    private final Team winner;
    private final int autobotsKilled;
    private final int decepticonsKilled;

    public AfterBattleState(List<Fighters> facedFighters, Team winner, int autobotsKilled, int decepticonsKilled) {
        this.facedFighters = facedFighters;
        this.winner = winner;
        this.autobotsKilled = autobotsKilled;
        this.decepticonsKilled = decepticonsKilled;
    }

    public List<Fighters> getFacedFighters() {
        return facedFighters;
    }

    public Team getWinner() {
        return winner;
    }

    public int getAutobotsKilled() {
        return autobotsKilled;
    }

    public int getDecepticonsKilled() {
        return decepticonsKilled;
    }
}
