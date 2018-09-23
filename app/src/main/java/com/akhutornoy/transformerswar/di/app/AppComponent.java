package com.akhutornoy.transformerswar.di.app;

import com.akhutornoy.transformerswar.App;
import com.akhutornoy.transformerswar.di.app.bins.AppModule;
import com.akhutornoy.transformerswar.di.app.bins.OkHttpClientModule;
import com.akhutornoy.transformerswar.di.app.bins.RetrofitModule;
import com.akhutornoy.transformerswar.di.app.bins.SharedPreferencesModule;
import com.akhutornoy.transformerswar.di.app.transformerlist.TransformerListInjectorModule;
import com.akhutornoy.transformerswar.di.app.transformerlist.addedit.AddEditFragmentInjectorModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        OkHttpClientModule.class,
        RetrofitModule.class,
        SharedPreferencesModule.class,
        TransformerListInjectorModule.class,
        AddEditFragmentInjectorModule.class,
})
public interface AppComponent extends AndroidInjector<App> {
}
