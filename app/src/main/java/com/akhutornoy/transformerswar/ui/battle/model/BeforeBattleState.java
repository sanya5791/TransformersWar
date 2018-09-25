package com.akhutornoy.transformerswar.ui.battle.model;

import java.util.List;

public class BeforeBattleState {
    private final List<Fighters> facedFighters;

    public BeforeBattleState(List<Fighters> facedFighters) {
        this.facedFighters = facedFighters;
    }

    public List<Fighters> getFacedFighters() {
        return facedFighters;
    }
}
