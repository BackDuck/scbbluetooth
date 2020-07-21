package com.example.scbbluetooth.di.components

import android.app.Application
import com.example.scbbluetooth.App
import com.example.scbbluetooth.data.DataModule
import com.example.scbbluetooth.data.network.NetworkModule
import com.example.scbbluetooth.di.modules.AppModule
import com.example.scbbluetooth.di.modules.builders.ActivityBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        DataModule::class,
        ActivityBuilder::class,
        AndroidSupportInjectionModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>() {

        @BindsInstance
        abstract fun application(application: Application): Builder

    }

}
