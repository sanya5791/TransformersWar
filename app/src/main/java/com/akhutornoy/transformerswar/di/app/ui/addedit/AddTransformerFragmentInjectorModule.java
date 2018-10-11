package com.akhutornoy.transformerswar.di.app.ui.addedit;

import com.akhutornoy.transformerswar.di.scopes.FragmentScope;
import com.akhutornoy.transformerswar.ui.addedit.AddTransformerFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AddTransformerFragmentInjectorModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = {
            AddTransformerModule.class,
    })
    public abstract AddTransformerFragment provideAddTransformerFragment();
}
