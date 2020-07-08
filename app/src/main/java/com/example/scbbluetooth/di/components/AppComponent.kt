package com.example.scbbluetooth.di.components

import android.app.Application
import com.example.scbbluetooth.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import com.example.scbbluetooth.di.modules.builder.ActivityBuilder
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ActivityBuilder::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>() {

        @BindsInstance
        abstract fun application(application: Application): Builder

    }

}