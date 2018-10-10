package com.akhutornoy.transformerswar.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
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
        String errorMessage = "";

        if (error instanceof HttpException) {
            try {
                ResponseBody responseBody = ((HttpException) error).response().errorBody();
                if (responseBody != null) {
                    errorMessage = error.getMessage() + ": " + responseBody.string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (errorMessage.isEmpty() && error.getMessage() != null) {
            errorMessage = error.getMessage();
        }

        showErrorLiveData.setValue(errorMessage);
    }
}
