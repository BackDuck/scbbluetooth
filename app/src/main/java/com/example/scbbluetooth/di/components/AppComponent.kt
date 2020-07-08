package com.example.scbbluetooth.di.components

import android.app.Application
import com.example.scbbluetooth.App
import com.example.scbbluetooth.di.modules.AppModule
import com.example.scbbluetooth.di.modules.builder.ActivityBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ActivityBuilder::class]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>() {

        @BindsInstance
        abstract fun application(application: Application): Builder
    }
}
