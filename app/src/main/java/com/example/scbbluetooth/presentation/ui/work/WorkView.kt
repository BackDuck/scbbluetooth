package com.example.scbbluetooth.presentation.ui.work

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.scbbluetooth.base.BaseView

interface WorkView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun startWatch()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun stopWatch()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeStatus(isWorking: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeTimer(s: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun refreshChrono(b: Long, d: String)

}