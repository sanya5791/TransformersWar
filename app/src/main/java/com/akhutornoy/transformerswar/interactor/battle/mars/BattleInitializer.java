package com.akhutornoy.transformerswar.interactor.battle.mars;

import com.akhutornoy.transformerswar.repository.cache.TransformerEntity;
import com.akhutornoy.transformerswar.ui.battle.model.Fighters;
import com.akhutornoy.transformerswar.ui.battle.model.Fighters.FightResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BattleInitializer {

    public List<Fighters> disposeTransformers(List<TransformerEntity> transformers) {
        List<Fighters> fightersList = new ArrayList<>();

        List<TransformerEntity> autobots = getTeam(transformers, TransformerEntity.AUTOBOT_TEAM);
        List<TransformerEntity> decepticons = getTeam(transformers, TransformerEntity.DECEPTICON_TEAM);

        for (TransformerEntity decepticon : decepticons) {
            Iterator<TransformerEntity> itAutobot = autobots.iterator();
            while (itAutobot.hasNext()) {
                TransformerEntity autobot = itAutobot.next();
                if (decepticon.getRank() == autobot.getRank()) {
                    fightersList.add(new Fighters(autobot, decepticon, FightResult.BOTH_ALIVE));
                    itAutobot.remove();
                    break;
                }
            }
        }

        return fightersList;
    }

    private List<TransformerEntity> getTeam(List<TransformerEntity> transformers, String team) {
        List<TransformerEntity> result = new LinkedList<>();
        for (TransformerEntity transformer : transformers) {
            if (transformer.getTeam().equals(team)) {
                result.add(transformer);
            }
        }
        return result;
    }
}
