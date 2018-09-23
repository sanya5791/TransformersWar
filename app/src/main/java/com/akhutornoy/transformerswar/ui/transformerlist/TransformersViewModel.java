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

    public TransformersViewModel(TransformerListInteractor transformerListInteractor, RatingCalculator ratingCalculator) {
        this.transformerListInteractor = transformerListInteractor;
        this.ratingCalculator = ratingCalculator;
    }

    public LiveData<List<TransformerModel>> getOnTransformersLoadedLiveData() {
        return onTransformersLoadedViewModel;
    }

    public void loadTransformers() {
        autoUnsubscribe(
                transformerListInteractor.loadTransformers()
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

    }
}
