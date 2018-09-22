package com.akhutornoy.transformerswar.di.app.transformerlist;

import com.akhutornoy.transformerswar.di.app.interactor.AllSparkProviderModule;
import com.akhutornoy.transformerswar.di.scopes.ActivityScope;
import com.akhutornoy.transformerswar.ui.TransformersActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TransformerListInjectorModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = { TransformerListModule.class, AllSparkProviderModule.class })
    public abstract TransformersActivity provideTransformersActivity();
}
