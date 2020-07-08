package com.example.scbbluetooth.presentation.ui

import android.os.Bundle
import com.example.scbbluetooth.R
import com.example.scbbluetooth.base.MoxyActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class RootActivity : MoxyActivity(), RootView {

    @Inject
    lateinit var presenterProvider: Provider<RootPresenter>

    private val presenter by moxyPresenter {
        presenterProvider.get()
    }

    override val layout = R.layout.activity_main
    override val containerId = R.id.container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(savedInstanceState != null)
    }
}
