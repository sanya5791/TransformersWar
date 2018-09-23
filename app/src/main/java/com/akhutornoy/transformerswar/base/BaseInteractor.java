package com.akhutornoy.transformerswar.base;

import com.akhutornoy.transformerswar.interactor.transformerlist.AllSparkProvider;

import io.reactivex.Single;

public class BaseInteractor {
    private final AllSparkProvider allSparkProvider;

    public BaseInteractor(AllSparkProvider allSparkProvider) {
        this.allSparkProvider = allSparkProvider;
    }

    protected Single<String> getAllStark() {
        return allSparkProvider.getAllSpark();
    }
}
