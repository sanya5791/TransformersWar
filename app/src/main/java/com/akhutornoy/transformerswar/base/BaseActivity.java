package com.akhutornoy.transformerswar.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import dagger.android.support.DaggerAppCompatActivity;


public abstract class BaseActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
    }

    @LayoutRes
    protected abstract int getContentViewId();
}
