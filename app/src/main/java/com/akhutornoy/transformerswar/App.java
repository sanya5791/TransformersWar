package com.akhutornoy.transformerswar;

import com.akhutornoy.transformerswar.di.app.DaggerAppComponent;
import com.akhutornoy.transformerswar.di.app.bins.AppModule;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import timber.log.Timber;

public class App extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {

//        DaggerAppComponent.create().inject(this);
        return DaggerAppComponent.builder()
//                .appModule(new AppModule(this))
                .build();
    }
}
