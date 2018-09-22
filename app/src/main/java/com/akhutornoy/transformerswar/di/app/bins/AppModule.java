package com.akhutornoy.transformerswar.di.app.bins;

import android.content.Context;

import com.akhutornoy.transformerswar.App;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    public App provideApp() {
        return app;
    }

    @Provides
    public Context provideContext() {
        return app;
    }
}
