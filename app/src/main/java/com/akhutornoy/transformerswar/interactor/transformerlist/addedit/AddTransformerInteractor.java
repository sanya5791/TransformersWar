package com.akhutornoy.transformerswar.interactor.transformerlist.addedit;

import com.akhutornoy.transformerswar.base.BaseInteractor;
import com.akhutornoy.transformerswar.interactor.transformerlist.AllSparkProvider;
import com.akhutornoy.transformerswar.repository.rest.NetworkApi;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;

import io.reactivex.Single;

public class AddTransformerInteractor extends BaseInteractor {
    private final NetworkApi api;

    public AddTransformerInteractor(AllSparkProvider allSparkProvider, NetworkApi api) {
        super(allSparkProvider);
        this.api = api;
    }

    public Single<Transformer> addTransformer(Transformer transformer) {
        return getAllStark()
                .flatMap(allSpark -> api.postTransformer(allSpark, transformer));
    }
}
