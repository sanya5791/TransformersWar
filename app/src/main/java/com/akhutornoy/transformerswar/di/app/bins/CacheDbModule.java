package com.akhutornoy.transformerswar.di.app.bins;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.akhutornoy.transformerswar.repository.cache.CacheDb;
import com.akhutornoy.transformerswar.repository.cache.TransformerDao;
import com.akhutornoy.transformerswar.repository.cache.ValidationDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheDbModule {
    @Provides
    @Singleton
    public CacheDb provideCacheDb(Context context) {
        return Room.inMemoryDatabaseBuilder(context, CacheDb.class)
                .build();
    }

    @Provides
    @Singleton
    public TransformerDao provideTransformerDao(CacheDb cacheDb) {
        return cacheDb.getTransformerDao();
    }

    @Provides
    @Singleton
    public ValidationDao provideValidationDao(CacheDb cacheDb) {
        return cacheDb.getValidationDao();
    }
}
