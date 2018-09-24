package com.akhutornoy.transformerswar.ui.transformerlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.akhutornoy.transformerswar.base.BaseViewModel;
import com.akhutornoy.transformerswar.interactor.transformerlist.RatingCalculator;
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
    private final MutableLiveData<String> onTransformerDeleteLiveData
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

    public LiveData<String> getOnTransformerDeleteLiveData() {
        return onTransformerDeleteLiveData;
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

    public void deleteTransformer(String transformerId) {
        autoUnsubscribe(
                transformerListInteractor.deleteTransformer(transformerId)
                    .compose(RxUtils.applySchedulersCompletable())
                    .compose(RxUtils.applyProgressViewCompletable(this))
                    .subscribe(
                            () -> onTransformerDeleteLiveData.setValue(transformerId),
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
}
