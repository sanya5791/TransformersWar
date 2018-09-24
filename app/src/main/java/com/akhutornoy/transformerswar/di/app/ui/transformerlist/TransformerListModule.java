package com.akhutornoy.transformerswar.di.app.ui.transformerlist;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import com.akhutornoy.transformerswar.di.scopes.FragmentScope;
import com.akhutornoy.transformerswar.interactor.transformerlist.RatingCalculator;
import com.akhutornoy.transformerswar.interactor.transformerlist.TransformerListInteractor;
import com.akhutornoy.transformerswar.ui.transformerlist.TransformersFragment;
import com.akhutornoy.transformerswar.ui.transformerlist.TransformersViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class TransformerListModule {

    @Provides
    @FragmentScope
    public TransformersViewModel provideTransformersViewModel(TransformersFragment fragment, ViewModelFactory factory) {
        return ViewModelProviders.of(fragment, factory).get(TransformersViewModel.class);
    }

    @Provides
    @FragmentScope
    public ViewModelFactory provideViewModelFactory(TransformerListInteractor interactor) {
        RatingCalculator ratingCalculator = new RatingCalculator();
        return new ViewModelFactory(interactor, ratingCalculator);
    }

    class ViewModelFactory implements ViewModelProvider.Factory {
        private final TransformerListInteractor interactor;
        private final RatingCalculator ratingCalculator;

        ViewModelFactory(TransformerListInteractor interactor, RatingCalculator ratingCalculator) {
            this.interactor = interactor;
            this.ratingCalculator = ratingCalculator;
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == TransformersViewModel.class) {
                return (T) new TransformersViewModel(interactor, ratingCalculator);
            }
            throw new IllegalArgumentException("Don't have ViewModel for '" + modelClass + "'");
        }
    }
}
