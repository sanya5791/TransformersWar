package com.akhutornoy.transformerswar.di.app.ui.addedit;

import com.akhutornoy.transformerswar.di.scopes.FragmentScope;
import com.akhutornoy.transformerswar.ui.addedit.EditTransformerFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class EditTransformerFragmentInjectorModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = {
            EditTransformerModule.class,
    })
    public abstract EditTransformerFragment provideAddTransformerFragment();
}
