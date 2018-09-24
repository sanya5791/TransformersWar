package com.akhutornoy.transformerswar.di.app.transformerlist.addedit;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import com.akhutornoy.transformerswar.di.scopes.FragmentScope;
import com.akhutornoy.transformerswar.interactor.transformerlist.AllSparkProvider;
import com.akhutornoy.transformerswar.interactor.transformerlist.addedit.AddEditTransformerInteractor;
import com.akhutornoy.transformerswar.repository.rest.NetworkApi;
import com.akhutornoy.transformerswar.ui.transformerlist.addedit.AddTransformerViewModel;
import com.akhutornoy.transformerswar.ui.transformerlist.addedit.EditTransformerFragment;
import com.akhutornoy.transformerswar.ui.transformerlist.addedit.EditTransformerViewModel;
import com.akhutornoy.transformerswar.utils.validation.ValidationManager;

import dagger.Module;
import dagger.Provides;

@Module
public class EditTransformerModule {

    @Provides
    @FragmentScope
    public AddTransformerViewModel provideEditTransformerViewModel(EditTransformerFragment fragment, ViewModelFactory factory) {
        return ViewModelProviders.of(fragment, factory).get(EditTransformerViewModel.class);
    }

    @Provides
    @FragmentScope
    public ViewModelFactory provideViewModelFactory(AllSparkProvider allSparkProvider, NetworkApi api, ValidationManager validationManager) {
        AddEditTransformerInteractor interactor = new AddEditTransformerInteractor(allSparkProvider, api);
        return new ViewModelFactory(interactor, validationManager);
    }

    @Provides
    @FragmentScope
    public ValidationManager provideValidationManager() {
        return new ValidationManager();
    }

    class ViewModelFactory implements ViewModelProvider.Factory {
        private final AddEditTransformerInteractor interactor;
        private final ValidationManager validationManager;

        ViewModelFactory(AddEditTransformerInteractor interactor, ValidationManager validationManager) {
            this.interactor = interactor;
            this.validationManager = validationManager;
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == EditTransformerViewModel.class) {
                return (T) new EditTransformerViewModel(interactor, validationManager);
            }
            throw new IllegalArgumentException("Don't have ViewModel for '" + modelClass + "'");
        }
    }
}
