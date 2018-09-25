package com.akhutornoy.transformerswar.interactor.battle.mars;

import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.ui.battle.model.Fighters;
import com.akhutornoy.transformerswar.ui.battle.model.Fighters.FightResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BattleInitializer {

    public List<Fighters> disposeTransformers(List<Transformer> transformers) {
        List<Fighters> fightersList = new ArrayList<>();

        List<Transformer> autobots = getTeam(transformers, Transformer.AUTOBOT_TEAM);
        List<Transformer> decepticons = getTeam(transformers, Transformer.DECEPTICON_TEAM);

        for (Transformer decepticon : decepticons) {
            Iterator<Transformer> itAutobot = autobots.iterator();
            while (itAutobot.hasNext()) {
                Transformer autobot = itAutobot.next();
                if (decepticon.getRank() == autobot.getRank()) {
                    fightersList.add(new Fighters(autobot, decepticon, FightResult.BOTH_ALIVE));
                    itAutobot.remove();
                    break;
                }
            }
        }

        return fightersList;
    }

    private List<Transformer> getTeam(List<Transformer> transformers, String team) {
        List<Transformer> result = new LinkedList<>();
        for (Transformer transformer : transformers) {
            if (transformer.getTeam().equals(team)) {
                result.add(transformer);
            }
        }
        return result;
    }
}
