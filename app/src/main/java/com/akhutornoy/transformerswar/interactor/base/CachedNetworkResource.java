package com.akhutornoy.transformerswar.interactor.base;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import timber.log.Timber;

public abstract class CachedNetworkResource<CacheData, ApiData> {

    public Flowable<CacheData> getData() {
        return validateCache()
                .andThen(getFromCache());
    }

    private Completable validateCache() {
        return getFromCache()
                .take(1)
                .flatMapSingle(this::isCacheValid)
                .doOnNext(isCacheValid -> Timber.d("load from %s", isCacheValid ? "Cache" : "API"))
                .flatMapCompletable(isCacheValid -> isCacheValid
                        ? Completable.complete()
                        : refreshCache());
    }

    private Completable refreshCache() {
        return getApiData()
                //todo you can handle api error here
                .map(this::mapApiDataToCacheData)
                .flatMapCompletable(this::insertToCache)
                .andThen(setCacheValidationStateOnApiReceived());
    }

    protected abstract Flowable<CacheData> getFromCache();

    protected abstract Single<Boolean> isCacheValid(CacheData cacheData);

    protected abstract Completable setCacheValidationStateOnApiReceived();

    protected abstract Single<ApiData> getApiData();

    protected abstract CacheData mapApiDataToCacheData(ApiData apiData);

    protected abstract Completable insertToCache(CacheData cacheData);
}
