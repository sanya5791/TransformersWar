package com.akhutornoy.transformerswar.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class BaseViewModel extends ViewModel {
    private final CompositeDisposable autoUnsubscribe = new CompositeDisposable();
    private final MutableLiveData<Boolean> showProgressLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> showErrorLiveData = new MutableLiveData<>();

    protected void autoUnsubscribe(Disposable disposable) {
        autoUnsubscribe.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        autoUnsubscribe.clear();
    }

    public LiveData<String> getShowErrorLiveData() {
        return showErrorLiveData;
    }

    public MutableLiveData<Boolean> getShowProgressBarLiveData() {
        return showProgressLiveData;
    }

    protected void showError(Throwable error) {
        Timber.e(error);
        if (error.getMessage() != null) {
            showErrorLiveData.setValue(error.getMessage());
        }
    }
}
