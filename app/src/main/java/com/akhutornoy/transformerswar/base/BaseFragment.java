package com.akhutornoy.transformerswar.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akhutornoy.transformerswar.ui.utils.AppDialog;

import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        initViewModelObservers();
        handleErrors();
        handleProgressBar();
    }

    protected void handleErrors() {
        if (getBaseViewModel() != null) {
            getBaseViewModel().getShowErrorLiveData().observe(this, this::handleErrorMessage);
        }
    }

    private void handleProgressBar() {
        if (getBaseViewModel() != null) {
            getBaseViewModel().getShowProgressBarLiveData().observe(this, needShow -> {
                if (needShow !=null && needShow) {
                    AppDialog.showProgressDialog(getActivity());
                } else {
                    AppDialog.hideProgressDialog();
                }
            });
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayoutId(), container, false);
        initViews(view);
        return view;
    }

    @LayoutRes
    protected abstract int getFragmentLayoutId();

    @Nullable
    protected abstract BaseViewModel getBaseViewModel();

    protected abstract void initViewModelObservers();

    protected abstract void initViews(View view);

    protected void handleErrorMessage(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}
