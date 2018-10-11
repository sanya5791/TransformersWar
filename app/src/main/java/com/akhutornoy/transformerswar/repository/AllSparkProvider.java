package com.akhutornoy.transformerswar.repository;

import com.akhutornoy.transformerswar.repository.rest.NetworkApi;
import com.akhutornoy.transformerswar.repository.sharedpreferences.AllSparkPreferences;

import io.reactivex.Single;

public class AllSparkProvider {
    public static final String TOKEN_PREFIX = "Bearer ";

    private final NetworkApi networkApi;
    private final AllSparkPreferences preferences;

    public AllSparkProvider(NetworkApi networkApi, AllSparkPreferences preferences) {
        this.networkApi = networkApi;
        this.preferences = preferences;
    }

    public Single<String> getAllSpark() {
        return Single.fromCallable(preferences::getAllSpark)
                .flatMap(allSpark -> allSpark.isEmpty()
                        ? createNewAllSpark()
                        : Single.just(allSpark));
    }

    private Single<String> createNewAllSpark() {
        return networkApi
                .getAllSpark()
                .map(TOKEN_PREFIX::concat)
                .doOnSuccess(preferences::setAllSpark);
    }
}
