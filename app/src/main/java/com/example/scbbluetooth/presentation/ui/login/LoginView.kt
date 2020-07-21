package com.example.scbbluetooth.presentation.ui.login

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface LoginView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun getFields()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(error: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSuccess()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun saveToken(token: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeActivity()
}