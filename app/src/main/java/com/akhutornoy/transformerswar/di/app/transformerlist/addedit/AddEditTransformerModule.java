package com.akhutornoy.transformerswar.di.app.transformerlist.addedit;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import com.akhutornoy.transformerswar.di.scopes.FragmentScope;
import com.akhutornoy.transformerswar.interactor.transformerlist.AllSparkProvider;
import com.akhutornoy.transformerswar.interactor.transformerlist.addedit.AddTransformerInteractor;
import com.akhutornoy.transformerswar.repository.rest.NetworkApi;
import com.akhutornoy.transformerswar.ui.transformerlist.addedit.AddTransformerFragment;
import com.akhutornoy.transformerswar.ui.transformerlist.addedit.AddTransformerViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class AddEditTransformerModule {

    @Provides
    @FragmentScope
    public AddTransformerViewModel provideAddTransformerViewModel(AddTransformerFragment fragment, ViewModelFactory factory) {
        return ViewModelProviders.of(fragment, factory).get(AddTransformerViewModel.class);
    }

    @Provides
    @FragmentScope
    public ViewModelFactory provideViewModelFactory(AllSparkProvider allSparkProvider, NetworkApi api) {
        AddTransformerInteractor interactor = new AddTransformerInteractor(allSparkProvider, api);
        return new ViewModelFactory(interactor);
    }


    class ViewModelFactory implements ViewModelProvider.Factory {
        private final AddTransformerInteractor interactor;

        ViewModelFactory(AddTransformerInteractor interactor) {
            this.interactor = interactor;
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == AddTransformerViewModel.class) {
                return (T) new AddTransformerViewModel(interactor);
            }
            throw new IllegalArgumentException("Don't have ViewModel for '" + modelClass + "'");
        }
    }
}
