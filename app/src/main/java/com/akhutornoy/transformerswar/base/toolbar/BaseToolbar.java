package com.akhutornoy.transformerswar.base.toolbar;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.akhutornoy.transformerswar.base.BaseActivity;

public class BaseToolbar implements IToolbar {
    private final BaseActivity activity;
    private @Nullable ActionBar actionBar;

    public BaseToolbar(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public void setToolbar(Toolbar toolbar, boolean showHomeAsUp) {
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }

    @Override
    public void setToolbarTitle(@StringRes int title) {
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    public void setToolbarSubtitle(@StringRes int subTitle) {
        if (actionBar != null) {
            actionBar.setTitle(subTitle);
        }
    }
}
