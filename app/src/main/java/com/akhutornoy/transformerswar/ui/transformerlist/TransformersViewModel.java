package com.akhutornoy.transformerswar.ui.transformerlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.akhutornoy.transformerswar.base.BaseViewModel;
import com.akhutornoy.transformerswar.interactor.battle.mars.RatingCalculator;
import com.akhutornoy.transformerswar.interactor.transformerlist.TransformerListInteractor;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

public class TransformersViewModel extends BaseViewModel {

    private final TransformerListInteractor transformerListInteractor;
    private final RatingCalculator ratingCalculator;

    private final MutableLiveData<List<TransformerModel>> onTransformersLoadedViewModel
            = new MutableLiveData<>();
    private final MutableLiveData<Transformer> onTransformerEditLiveData
            = new MutableLiveData<>();
    private final MutableLiveData<TransformerModel> onTransformerDeleteLiveData
            = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Transformer>> onStartBattleLiveData
            = new MutableLiveData<>();

    private List<Transformer> transformersApi = new ArrayList<>();

    public TransformersViewModel(TransformerListInteractor transformerListInteractor, RatingCalculator ratingCalculator) {
        this.transformerListInteractor = transformerListInteractor;
        this.ratingCalculator = ratingCalculator;
    }

    public LiveData<List<TransformerModel>> getOnTransformersLoadedLiveData() {
        return onTransformersLoadedViewModel;
    }

    public LiveData<Transformer> getOnTransformerEditLiveData() {
        return onTransformerEditLiveData;
    }

    public LiveData<TransformerModel> getOnTransformerDeleteLiveData() {
        return onTransformerDeleteLiveData;
    }

    public LiveData<ArrayList<Transformer>> getOnStartBattleLiveData() {
        return onStartBattleLiveData;
    }

    public void loadTransformers() {
        autoUnsubscribe(
                transformerListInteractor.loadTransformers()
                        .doOnSuccess(transformers -> transformersApi = transformers)
                        .map(this::mapToTransformersModel)
                        .compose(RxUtils.applySchedulersSingle())
                        .compose(RxUtils.applyProgressViewSingle(this))
                        .subscribe(
                                onTransformersLoadedViewModel::setValue,
                                this::showError
                        )
        );
    }

    private List<TransformerModel> mapToTransformersModel(List<Transformer> transformers) {
        List<TransformerModel> result = new ArrayList<>(transformers.size());
        for (Transformer transformer : transformers) {
            result.add(mapToTransformerModel(transformer));
        }
        return result;
    }

    private TransformerModel mapToTransformerModel(Transformer transformer) {
        return new TransformerModel.Builder()
                .setId(transformer.getId())
                .setName(transformer.getName())
                .setRate(String.valueOf(ratingCalculator.calculate(transformer)))
                .setImageUrl(transformer.getTeam_icon())
                .build();
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
        for (Transformer transformer : transformersApi) {
            if (transformer.getId().equals(transformerModel.getId())) {
                onTransformerEditLiveData.setValue(transformer);
                return;
            }
        }
        throw new IllegalArgumentException(String.format("Can't find Transformer name=%s, with id=%s for edit", transformerModel.getName(), transformerModel.getId()));
    }

    public void startBattle() {
        onStartBattleLiveData.setValue(new ArrayList<>(transformersApi));
    }

    public void resetLiveData() {
        onTransformersLoadedViewModel.setValue(null);
        onStartBattleLiveData.setValue(null);
        onTransformerEditLiveData.setValue(null);
        onTransformerDeleteLiveData.setValue(null);
    }
}
