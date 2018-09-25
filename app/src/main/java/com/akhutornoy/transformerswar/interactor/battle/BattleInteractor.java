package com.akhutornoy.transformerswar.interactor.battle;

import com.akhutornoy.transformerswar.base.BaseInteractor;
import com.akhutornoy.transformerswar.interactor.battle.mars.Mars;
import com.akhutornoy.transformerswar.interactor.transformerlist.AllSparkProvider;
import com.akhutornoy.transformerswar.repository.rest.NetworkApi;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.ui.battle.model.AfterBattleState;
import com.akhutornoy.transformerswar.ui.battle.model.BeforeBattleState;
import com.akhutornoy.transformerswar.ui.battle.model.Fighters;
import com.akhutornoy.transformerswar.ui.battle.model.Team;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class BattleInteractor extends BaseInteractor {

    private final NetworkApi api;
    private final Mars mars;

    public BattleInteractor(AllSparkProvider allSparkProvider, NetworkApi api, Mars mars) {
        super(allSparkProvider);
        this.api = api;
        this.mars = mars;
    }

    public Single<BeforeBattleState> prepareToBattle(List<Transformer> transformers) {
        return Single.fromCallable(() -> mars.disposeTransformers(transformers))
                .map(BeforeBattleState::new);
    }

    public Single<AfterBattleState> battle(BeforeBattleState beforeBattleState) {
        return Single.fromCallable(() -> mars.startBattle(beforeBattleState))
                .zipWith(getAllStark(), this::removeKilledTransformers)
                .flatMap(item -> item);
    }

    private Single<AfterBattleState> removeKilledTransformers(AfterBattleState afterBattle, String allSpark) {
        return Observable.just(afterBattle)
                .flatMapIterable(afterBattleState -> getKilledTransformers(afterBattle))
                .map(Transformer::getId)
                .concatMapCompletable(id -> api.deleteTransformer(allSpark, id))
                .andThen(Single.just(afterBattle));
    }

    private List<Transformer> getKilledTransformers(AfterBattleState afterBattle) {
        List<Fighters> facedFighters = afterBattle.getFacedFighters();

        return afterBattle.getWinner() == Team.TOTAL_ANNIHILATION
                ? getAllTransformers(facedFighters)
                : getKilledFighters(facedFighters);
    }

    private List<Transformer> getKilledFighters(List<Fighters> fightersList) {
        List<Transformer> killedTransformers = new ArrayList<>(fightersList.size());
        for (Fighters fighters : fightersList) {
            Fighters.FightResult fightResult = fighters.getFightResult();
            switch (fightResult) {
                case AUTOBOT_WINNER:
                    killedTransformers.add(fighters.getDecepticon());
                    break;
                case DECIPTICON_WINNER:
                    killedTransformers.add(fighters.getAutobot());
                    break;
                case BOTH_KILLED:
                    killedTransformers.add(fighters.getAutobot());
                    killedTransformers.add(fighters.getDecepticon());
                    break;
            }
        }
        return killedTransformers;
    }

    private List<Transformer> getAllTransformers(List<Fighters> facedFighters) {
        List<Transformer> result = new ArrayList<>();
        for (Fighters fighters : facedFighters) {
            result.add(fighters.getAutobot());
            result.add(fighters.getDecepticon());
        }
        return result;
    }
}
