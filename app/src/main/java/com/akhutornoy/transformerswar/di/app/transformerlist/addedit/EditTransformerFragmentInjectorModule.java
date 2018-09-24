package com.akhutornoy.transformerswar.di.app.transformerlist.addedit;

import com.akhutornoy.transformerswar.di.app.interactor.AllSparkProviderModule;
import com.akhutornoy.transformerswar.di.scopes.FragmentScope;
import com.akhutornoy.transformerswar.ui.transformerlist.addedit.EditTransformerFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class EditTransformerFragmentInjectorModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = {
            EditTransformerModule.class,
            AllSparkProviderModule.class,
    })
    public abstract EditTransformerFragment provideAddTransformerFragment();
}
