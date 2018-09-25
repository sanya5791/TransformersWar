package com.akhutornoy.transformerswar.di.app.ui.battle;

import com.akhutornoy.transformerswar.di.app.interactor.AllSparkProviderModule;
import com.akhutornoy.transformerswar.di.app.interactor.BattleInteractorModule;
import com.akhutornoy.transformerswar.di.scopes.FragmentScope;
import com.akhutornoy.transformerswar.ui.battle.BattleFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BattleFragmentInjectorModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = {
            BattleModule.class,
            AllSparkProviderModule.class,
            BattleInteractorModule.class,
    })
    public abstract BattleFragment provideBattleFragment();
}
