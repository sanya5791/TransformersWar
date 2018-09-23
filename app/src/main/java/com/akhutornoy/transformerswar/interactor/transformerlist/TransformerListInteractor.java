package com.akhutornoy.transformerswar.interactor.transformerlist;

import com.akhutornoy.transformerswar.base.BaseInteractor;
import com.akhutornoy.transformerswar.repository.rest.NetworkApi;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformers;

import java.util.List;

import io.reactivex.Single;

public class TransformerListInteractor extends BaseInteractor {
    private final NetworkApi api;

    public TransformerListInteractor(AllSparkProvider allSparkProvider, NetworkApi api) {
        super(allSparkProvider);
        this.api = api;
    }

    public Single<List<Transformer>> loadTransformers() {
        return getAllStark()
                .flatMap(api::getTransformers)
                .map(Transformers::getTransformers);
    }
}
