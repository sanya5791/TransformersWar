package com.akhutornoy.transformerswar.di.app;

import com.akhutornoy.transformerswar.App;
import com.akhutornoy.transformerswar.di.app.bins.AppModule;
import com.akhutornoy.transformerswar.di.app.bins.CacheDbModule;
import com.akhutornoy.transformerswar.di.app.bins.OkHttpClientModule;
import com.akhutornoy.transformerswar.di.app.bins.RetrofitModule;
import com.akhutornoy.transformerswar.di.app.bins.SharedPreferencesModule;
import com.akhutornoy.transformerswar.di.app.ui.battle.BattleFragmentInjectorModule;
import com.akhutornoy.transformerswar.di.app.ui.transformerlist.TransformersFragmentInjectorModule;
import com.akhutornoy.transformerswar.di.app.ui.addedit.AddTransformerFragmentInjectorModule;
import com.akhutornoy.transformerswar.di.app.ui.addedit.EditTransformerFragmentInjectorModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        CacheDbModule.class,
        OkHttpClientModule.class,
        RetrofitModule.class,
        SharedPreferencesModule.class,
        TransformersFragmentInjectorModule.class,
        AddTransformerFragmentInjectorModule.class,
        EditTransformerFragmentInjectorModule.class,
        BattleFragmentInjectorModule.class
})
public interface AppComponent extends AndroidInjector<App> {
}
