package com.example.scbbluetooth.presentation.ui.login

import com.example.scbbluetooth.models.User
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface LoginView: MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun login(type: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun getText()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun sendErrorToast()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun sendTestToast(data: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun getBtAddress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun saveUser(user: User)
}
