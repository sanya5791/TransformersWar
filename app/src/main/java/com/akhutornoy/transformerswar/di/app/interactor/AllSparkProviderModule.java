package com.akhutornoy.transformerswar.di.app.interactor;

import com.akhutornoy.transformerswar.interactor.transformerlist.AllSparkProvider;
import com.akhutornoy.transformerswar.repository.rest.NetworkApi;
import com.akhutornoy.transformerswar.repository.sharedpreferences.AllSparkPreferences;

import dagger.Module;
import dagger.Provides;

@Module
public class AllSparkProviderModule {

    @Provides
    public AllSparkProvider provideAllSparkProvider(NetworkApi networkApi, AllSparkPreferences preferences) {
        return new AllSparkProvider(networkApi, preferences);
    }
}
