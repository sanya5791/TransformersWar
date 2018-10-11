package com.akhutornoy.transformerswar.di.app.repository;

import com.akhutornoy.transformerswar.repository.AllSparkProvider;
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
