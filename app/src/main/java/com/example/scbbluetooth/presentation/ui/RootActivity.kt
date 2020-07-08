package com.example.scbbluetooth.presentation.ui

import android.os.Bundle
import com.example.scbbluetooth.R
import com.example.scbbluetooth.base.MoxyActivity
import javax.inject.Inject
import javax.inject.Provider

class RootActivity : MoxyActivity(),
    RootView {

    @Inject
    lateinit var presenterProvider: Provider<RootPresenter>

    override val layout = R.layout.activity_main
    override val containerId = R.id.container
}
