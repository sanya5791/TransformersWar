package com.akhutornoy.transformerswar.utils;

import com.akhutornoy.transformerswar.base.BaseViewModel;

import io.reactivex.CompletableTransformer;
import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {
    public static <T> SingleTransformer<T, T> applySchedulersSingle() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static CompletableTransformer applySchedulersCompletable() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> applySchedulersObservable() {
        return applySchedulersObservable(false);
    }

    public static <T> ObservableTransformer<T, T> applySchedulersObservable(boolean delayError) {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), delayError);
    }

    public static <T> MaybeTransformer<T, T> applySchedulersMaybe() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> SingleTransformer<T, T> applyProgressViewSingle(BaseViewModel viewModel) {
        return upstream -> upstream
                .doOnSubscribe(ignored -> showProgress(viewModel))
                .doFinally(() ->hideProgress(viewModel));
    }

    public static CompletableTransformer applyProgressViewCompletable(BaseViewModel viewModel) {
        return upstream -> upstream
                .doOnSubscribe(ignored -> showProgress(viewModel))
                .doFinally(() ->hideProgress(viewModel));
    }

    private static void showProgress(BaseViewModel viewModel) {
        viewModel.getShowProgressBarLiveData().setValue(true);
    }

    private static void hideProgress(BaseViewModel viewModel) {
        viewModel.getShowProgressBarLiveData().setValue(false);
    }
}
