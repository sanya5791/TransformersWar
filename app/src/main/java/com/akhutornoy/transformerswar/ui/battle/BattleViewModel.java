package com.akhutornoy.transformerswar.ui.battle;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.akhutornoy.transformerswar.base.BaseViewModel;
import com.akhutornoy.transformerswar.interactor.battle.BattleInteractor;
import com.akhutornoy.transformerswar.repository.cache.TransformerEntity;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.ui.battle.model.AfterBattleState;
import com.akhutornoy.transformerswar.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class BattleViewModel extends BaseViewModel {

    private final BattleInteractor battleInteractor;

    private final MutableLiveData<AfterBattleState> onAfterBattleViewModel =
            new MutableLiveData<>();

    public BattleViewModel(BattleInteractor battleInteractor) {
        this.battleInteractor = battleInteractor;
    }

    public LiveData<AfterBattleState> getOnAfterBattleViewModel() {
        return onAfterBattleViewModel;
    }

    public void battle(List<TransformerEntity> transformers) {
        autoUnsubscribe(
                Single.just(transformers)
                        .map(this::map)
                        .flatMap(battleInteractor::prepareToBattle)
                        .flatMap(battleInteractor::battle)
                        .compose(RxUtils.applySchedulersSingle())
                        .compose(RxUtils.applyProgressViewSingle(this))
                        .subscribe(
                                onAfterBattleViewModel::setValue,
                                this::showError
                        )
        );
    }

    @Deprecated
    private List<Transformer> map(List<TransformerEntity> source) {
        List<Transformer> result = new ArrayList<>(source.size());
        for (TransformerEntity item : source) {
            result.add(map(item));
        }
        return result;
    }

    @Deprecated
    private Transformer map(TransformerEntity item) {
        return new Transformer(
                item.getId(),
                item.getName(),
                item.getTeam(),
                item.getStrength(),
                item.getIntelligence(),
                item.getSpeed(),
                item.getEndurance(),
                item.getRank(),
                item.getCourage(),
                item.getFirepower(),
                item.getSkill(),
                item.getTeam_icon()
        );
    }
}
