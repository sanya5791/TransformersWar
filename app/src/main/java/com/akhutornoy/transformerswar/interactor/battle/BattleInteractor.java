package com.akhutornoy.transformerswar.interactor.battle;

import com.akhutornoy.transformerswar.base.BaseInteractor;
import com.akhutornoy.transformerswar.interactor.battle.mars.Mars;
import com.akhutornoy.transformerswar.interactor.battle.mars.BattleInitializer;
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
import timber.log.Timber;

public class BattleInteractor extends BaseInteractor {

    private final NetworkApi api;
    private final BattleInitializer battleInitializer;
    private final Mars mars;

    public BattleInteractor(AllSparkProvider allSparkProvider, NetworkApi api, BattleInitializer battleInitializer, Mars mars) {
        super(allSparkProvider);
        this.api = api;
        this.battleInitializer = battleInitializer;
        this.mars = mars;
    }

    public Single<BeforeBattleState> prepareToBattle(List<Transformer> transformers) {
        return Single.fromCallable(() -> battleInitializer.disposeTransformers(transformers))
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
                .doOnNext(transformer -> Timber.d("Remove Killed '%s' from server", transformer.getName()))
                .map(Transformer::getId)
                .flatMapCompletable(id -> api.deleteTransformer(allSpark, id))
                .andThen(Single.just(afterBattle))
                .doOnSuccess(item -> Timber.e("All Killed Transformers are deleted"));
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
