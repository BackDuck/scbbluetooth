package com.example.scbbluetooth.presentation.ui

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ErrorView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onSettingsClick()
}
