package com.example.scbbluetooth.di.modules.builder

import com.example.scbbluetooth.presentation.ui.RootActivity
import com.example.scbbluetooth.presentation.ui.di.RootModule
import com.example.scbbluetooth.presentation.ui.di.RootScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [RootModule::class])
    @RootScope
    abstract fun provideRootActivity(): RootActivity

}
