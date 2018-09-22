package com.akhutornoy.transformerswar.di.app.bins;

import android.content.Context;

import com.akhutornoy.transformerswar.repository.sharedpreferences.AllSparkPreferences;
import com.akhutornoy.transformerswar.repository.sharedpreferences.Preferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferencesModule {

    @Provides
    @Singleton
    public AllSparkPreferences provideAllSparkPreferences(Preferences preferences) {
        return new AllSparkPreferences(preferences);
    }

    @Provides
    @Singleton
    public Preferences providePreferences(Context context) {
        return new Preferences(context);
    }
}
