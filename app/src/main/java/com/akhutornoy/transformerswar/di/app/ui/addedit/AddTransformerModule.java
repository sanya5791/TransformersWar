package com.akhutornoy.transformerswar.di.app.ui.addedit;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import com.akhutornoy.transformerswar.di.app.interactor.InteractorsModule;
import com.akhutornoy.transformerswar.di.app.repository.TransformerRepositoryModule;
import com.akhutornoy.transformerswar.di.scopes.FragmentScope;
import com.akhutornoy.transformerswar.interactor.addedit.AddEditTransformerInteractor;
import com.akhutornoy.transformerswar.ui.addedit.AddTransformerFragment;
import com.akhutornoy.transformerswar.ui.addedit.AddTransformerViewModel;
import com.akhutornoy.transformerswar.ui.utils.validation.ValidationManager;

import dagger.Module;
import dagger.Provides;

@Module(includes = {TransformerRepositoryModule.class, InteractorsModule.class})
public class AddTransformerModule {

    @Provides
    @FragmentScope
    public AddTransformerViewModel provideAddTransformerViewModel(AddTransformerFragment fragment, ViewModelFactory factory) {
        return ViewModelProviders.of(fragment, factory).get(AddTransformerViewModel.class);
    }

    @Provides
    @FragmentScope
    public ViewModelFactory provideViewModelFactory(AddEditTransformerInteractor interactor, ValidationManager validationManager) {
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
            if (modelClass == AddTransformerViewModel.class) {
                return (T) new AddTransformerViewModel(interactor, validationManager);
            }
            throw new IllegalArgumentException("Don't have ViewModel for '" + modelClass + "'");
        }
    }
}
