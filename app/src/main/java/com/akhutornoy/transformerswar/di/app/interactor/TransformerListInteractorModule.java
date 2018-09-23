package com.akhutornoy.transformerswar.di.app.interactor;

import com.akhutornoy.transformerswar.interactor.transformerlist.AllSparkProvider;
import com.akhutornoy.transformerswar.interactor.transformerlist.TransformerListInteractor;
import com.akhutornoy.transformerswar.repository.rest.NetworkApi;

import dagger.Module;
import dagger.Provides;

@Module
public class TransformerListInteractorModule {
    @Provides
    public TransformerListInteractor provideTransformerListInteractor(
            AllSparkProvider allSparkProvider, NetworkApi networkApi) {
        return new TransformerListInteractor(allSparkProvider, networkApi);
    }
}
