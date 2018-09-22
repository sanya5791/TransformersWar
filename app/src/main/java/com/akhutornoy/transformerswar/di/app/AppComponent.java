package com.akhutornoy.transformerswar.di.app;

import com.akhutornoy.transformerswar.App;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
//        AppModule::class,
//        RoomModule::class,
//        RefillListFragmentInjectorModule::class,
//        RefillDetailsFragmentInjectorModule::class
})
public interface AppComponent extends AndroidInjector<App> {
}
