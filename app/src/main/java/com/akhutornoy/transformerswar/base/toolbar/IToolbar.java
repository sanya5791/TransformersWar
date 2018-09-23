package com.akhutornoy.transformerswar.base.toolbar;

import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;

public interface IToolbar {
    void setToolbar(Toolbar toolbar, boolean showHomeAsUp);
    void setToolbarTitle(@StringRes int title);
    void setToolbarSubtitle(@StringRes int subTitle);
}
