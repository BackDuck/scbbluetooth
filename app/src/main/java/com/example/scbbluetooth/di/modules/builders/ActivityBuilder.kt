package com.example.scbbluetooth.di.modules.builders

import com.example.scbbluetooth.presentation.ui.RootActivity
import com.example.scbbluetooth.presentation.ui.RootModule
import com.example.scbbluetooth.presentation.ui.RootScope
import com.example.scbbluetooth.presentation.ui.work.WorkActivity
import com.example.scbbluetooth.presentation.ui.work.WorkModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [RootModule::class])
    @RootScope
    abstract fun provideRootActivity(): RootActivity

    @ContributesAndroidInjector(modules = [WorkModule::class])
    @RootScope
    abstract fun provideWorkActivity(): WorkActivity

}