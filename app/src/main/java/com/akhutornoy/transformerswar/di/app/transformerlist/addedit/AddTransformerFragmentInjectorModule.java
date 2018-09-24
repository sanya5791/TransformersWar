package com.akhutornoy.transformerswar.di.app.transformerlist.addedit;

import com.akhutornoy.transformerswar.di.app.interactor.AllSparkProviderModule;
import com.akhutornoy.transformerswar.di.scopes.FragmentScope;
import com.akhutornoy.transformerswar.ui.transformerlist.addedit.AddTransformerFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AddTransformerFragmentInjectorModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = {
            AddTransformerModule.class,
            AllSparkProviderModule.class,
    })
    public abstract AddTransformerFragment provideAddTransformerFragment();
}
