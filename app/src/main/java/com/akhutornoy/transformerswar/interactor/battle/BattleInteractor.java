package com.akhutornoy.transformerswar.interactor.battle;

import com.akhutornoy.transformerswar.interactor.battle.mars.Mars;
import com.akhutornoy.transformerswar.repository.TransformersRepository;
import com.akhutornoy.transformerswar.repository.cache.TransformerEntity;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.ui.battle.model.AfterBattleState;
import com.akhutornoy.transformerswar.ui.battle.model.BeforeBattleState;
import com.akhutornoy.transformerswar.ui.battle.model.Fighters;
import com.akhutornoy.transformerswar.ui.battle.model.Team;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class BattleInteractor {

    private final TransformersRepository repository;
    private final Mars mars;

    public BattleInteractor(TransformersRepository repository, Mars mars) {
        this.mars = mars;
        this.repository = repository;
    }

    public Single<BeforeBattleState> prepareToBattle(List<TransformerEntity> transformers) {
        return Single.fromCallable(() -> mars.disposeTransformers(transformers))
                .map(BeforeBattleState::new);
    }

    public Single<AfterBattleState> battle(BeforeBattleState beforeBattleState) {
        return Single.fromCallable(() -> mars.startBattle(beforeBattleState))
                .flatMap(this::removeKilledTransformers);
    }

    private Single<AfterBattleState> removeKilledTransformers(AfterBattleState afterBattle) {
        return Observable.just(afterBattle)
                .flatMapIterable(afterBattleState -> getKilledTransformers(afterBattle))
                .map(TransformerEntity::getId)
                .concatMapCompletable(repository::deleteTransformer)
                .andThen(Single.just(afterBattle));
    }

    private List<TransformerEntity> getKilledTransformers(AfterBattleState afterBattle) {
        List<Fighters> facedFighters = afterBattle.getFacedFighters();

        return afterBattle.getWinner() == Team.TOTAL_ANNIHILATION
                ? getAllTransformers(facedFighters)
                : getKilledFighters(facedFighters);
    }

    private List<TransformerEntity> getKilledFighters(List<Fighters> fightersList) {
        List<TransformerEntity> killedTransformers = new ArrayList<>(fightersList.size());
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

    private List<TransformerEntity> getAllTransformers(List<Fighters> facedFighters) {
        List<TransformerEntity> result = new ArrayList<>();
        for (Fighters fighters : facedFighters) {
            result.add(fighters.getAutobot());
            result.add(fighters.getDecepticon());
        }
        return result;
    }
}
