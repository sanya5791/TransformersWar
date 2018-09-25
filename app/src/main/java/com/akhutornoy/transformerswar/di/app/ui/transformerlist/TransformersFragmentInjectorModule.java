package com.akhutornoy.transformerswar.di.app.ui.transformerlist;

import com.akhutornoy.transformerswar.di.app.interactor.AllSparkProviderModule;
import com.akhutornoy.transformerswar.di.app.interactor.TransformerListInteractorModule;
import com.akhutornoy.transformerswar.di.scopes.FragmentScope;
import com.akhutornoy.transformerswar.ui.transformerlist.TransformersFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TransformersFragmentInjectorModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = {
            TransformersModule.class,
            AllSparkProviderModule.class,
            TransformerListInteractorModule.class,
    })
    public abstract TransformersFragment provideTransformersFragment();
}
