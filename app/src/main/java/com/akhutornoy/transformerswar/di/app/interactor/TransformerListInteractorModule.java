package com.akhutornoy.transformerswar.di.app.interactor;

import com.akhutornoy.transformerswar.interactor.allspark.AllSparkProvider;
import com.akhutornoy.transformerswar.interactor.transformerlist.TransformerListInteractor;
import com.akhutornoy.transformerswar.repository.cache.TransformerDao;
import com.akhutornoy.transformerswar.repository.cache.ValidationDao;
import com.akhutornoy.transformerswar.repository.rest.NetworkApi;

import dagger.Module;
import dagger.Provides;

@Module
public class TransformerListInteractorModule {
    @Provides
    public TransformerListInteractor provideTransformerListInteractor(
            AllSparkProvider allSparkProvider, NetworkApi networkApi, TransformerDao transformerDao, ValidationDao validationDao) {
        return new TransformerListInteractor(allSparkProvider, networkApi, transformerDao, validationDao);
    }
}
