package com.akhutornoy.transformerswar.ui.battle;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.akhutornoy.transformerswar.base.BaseViewModel;
import com.akhutornoy.transformerswar.interactor.battle.BattleInteractor;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.ui.battle.model.AfterBattleState;
import com.akhutornoy.transformerswar.utils.RxUtils;

import java.util.List;

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

    public void battle(List<Transformer> transformers) {
        autoUnsubscribe(
                battleInteractor.prepareToBattle(transformers)
                        .flatMap(battleInteractor::battle)
                        .compose(RxUtils.applySchedulersSingle())
                        .compose(RxUtils.applyProgressViewSingle(this))
                        .subscribe(
                                onAfterBattleViewModel::setValue,
                                this::showError
                        )
        );
    }
}
