package com.akhutornoy.transformerswar.ui.transformerlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.akhutornoy.transformerswar.base.BaseViewModel;
import com.akhutornoy.transformerswar.interactor.battle.mars.RatingCalculator;
import com.akhutornoy.transformerswar.interactor.transformerlist.TransformerListInteractor;
import com.akhutornoy.transformerswar.repository.cache.TransformerEntity;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

public class TransformersViewModel extends BaseViewModel {

    private final TransformerListInteractor transformerListInteractor;
    private final RatingCalculator ratingCalculator;

    private final MutableLiveData<List<TransformerModel>> onTransformersLoadedViewModel
            = new MutableLiveData<>();
    private final MutableLiveData<TransformerEntity> onTransformerEditLiveData
            = new MutableLiveData<>();
    private final MutableLiveData<TransformerModel> onTransformerDeleteLiveData
            = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<TransformerEntity>> onStartBattleLiveData
            = new MutableLiveData<>();

    public TransformersViewModel(TransformerListInteractor transformerListInteractor, RatingCalculator ratingCalculator) {
        this.transformerListInteractor = transformerListInteractor;
        this.ratingCalculator = ratingCalculator;
    }

    public LiveData<List<TransformerModel>> getOnTransformersLoadedLiveData() {
        return onTransformersLoadedViewModel;
    }

    public LiveData<TransformerEntity> getOnTransformerEditLiveData() {
        return onTransformerEditLiveData;
    }

    public LiveData<TransformerModel> getOnTransformerDeleteLiveData() {
        return onTransformerDeleteLiveData;
    }

    public LiveData<ArrayList<TransformerEntity>> getOnStartBattleLiveData() {
        return onStartBattleLiveData;
    }

    public void loadTransformers() {
        autoUnsubscribe(
                transformerListInteractor.getTransformers()
                        .map(this::mapToTransformersModel)
                        .compose(RxUtils.applySchedulersFlowable())
                        .compose(RxUtils.applyProgressViewFlowable(this))
                        .subscribe(
                                onTransformersLoadedViewModel::setValue,
                                this::showError
                        )
        );
    }

    private List<TransformerModel> mapToTransformersModel(List<TransformerEntity> transformers) {
        List<TransformerModel> result = new ArrayList<>(transformers.size());
        for (TransformerEntity transformer : transformers) {
            result.add(mapToTransformerModel(transformer));
        }
        return result;
    }

    private TransformerModel mapToTransformerModel(TransformerEntity transformer) {
        return new TransformerModel.Builder()
                .setId(transformer.getId())
                .setName(transformer.getName())
                .setRate(String.valueOf(ratingCalculator.calculate(map(transformer))))
                .setImageUrl(transformer.getTeam_icon())
                .build();
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

    public void deleteTransformer(TransformerModel transformer) {
        autoUnsubscribe(
                transformerListInteractor.deleteTransformer(transformer.getId())
                    .compose(RxUtils.applySchedulersCompletable())
                    .compose(RxUtils.applyProgressViewCompletable(this))
                    .subscribe(
                            () -> onTransformerDeleteLiveData.setValue(transformer),
                            this::showError
                    )
        );
    }

    public void editTransformer(TransformerModel transformerModel) {
        autoUnsubscribe(
                transformerListInteractor.getTransformerById(transformerModel.getId())
                        .compose(RxUtils.applySchedulersSingle())
                        .compose(RxUtils.applyProgressViewSingle(this))
                        .subscribe(
                                onTransformerEditLiveData::setValue,
                                this::showError
                        )
        );
    }

    public void startBattle() {
        autoUnsubscribe(
                transformerListInteractor.getTransformers()
                        .take(1)
                        .map(ArrayList::new)
                        .compose(RxUtils.applySchedulersFlowable())
                        .compose(RxUtils.applyProgressViewFlowable(this))
                        .subscribe(
                                onStartBattleLiveData::setValue,
                                this::showError
                        )
        );
    }

    public void resetLiveData() {
        onTransformersLoadedViewModel.setValue(null);
        onStartBattleLiveData.setValue(null);
        onTransformerEditLiveData.setValue(null);
        onTransformerDeleteLiveData.setValue(null);
    }
}
