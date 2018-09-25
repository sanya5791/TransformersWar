package com.akhutornoy.transformerswar.di.app.ui.battle;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import com.akhutornoy.transformerswar.di.scopes.FragmentScope;
import com.akhutornoy.transformerswar.interactor.battle.BattleInteractor;
import com.akhutornoy.transformerswar.ui.battle.BattleFragment;
import com.akhutornoy.transformerswar.ui.battle.BattleViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class BattleModule {

    @Provides
    @FragmentScope
    public BattleViewModel provideBattleViewModel(BattleFragment fragment, ViewModelFactory factory) {
        return ViewModelProviders.of(fragment, factory).get(BattleViewModel.class);
    }

    @Provides
    @FragmentScope
    public ViewModelFactory provideViewModelFactory(BattleInteractor interactor) {
        return new ViewModelFactory(interactor);
    }

    class ViewModelFactory implements ViewModelProvider.Factory {
        private final BattleInteractor interactor;

        ViewModelFactory(BattleInteractor interactor) {
            this.interactor = interactor;
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == BattleViewModel.class) {
                return (T) new BattleViewModel(interactor);
            }
            throw new IllegalArgumentException("Don't have ViewModel for '" + modelClass + "'");
        }
    }
}
