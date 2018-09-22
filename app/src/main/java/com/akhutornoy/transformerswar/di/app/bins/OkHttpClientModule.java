package com.akhutornoy.transformerswar.di.app.bins;

import com.akhutornoy.transformerswar.App;
import com.akhutornoy.transformerswar.BuildConfig;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class OkHttpClientModule {
    private static final String REST_CACHE = "REST_CACHE";
    private static final long CACHE_SIZE = 50 * 1024 * 1024;

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Cache cache) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            builder.addInterceptor(logging);
        }

        return builder.build();
    }

    @Provides
    @Singleton
    public Cache provideCache(App app) {
        File file = new File(app.getCacheDir(), REST_CACHE);
        return new Cache(file, CACHE_SIZE);
    }
}
