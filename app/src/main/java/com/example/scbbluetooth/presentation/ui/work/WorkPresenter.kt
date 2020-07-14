package com.example.scbbluetooth.presentation.ui.work

import android.os.SystemClock
import com.example.scbbluetooth.base.MoxyPresenter
import moxy.InjectViewState
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@InjectViewState
class WorkPresenter @Inject constructor() : MoxyPresenter<WorkView>() {

    private var status = 1
    private var workTimeInSec: Long = 0

    fun prepareChronometer() {
        // TODO: Get status and time (if working) from db
        val calendar: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd.MM.yy")
        val currentDate: String = sdf.format(calendar.time)
        viewState.refreshChrono(SystemClock.elapsedRealtime(), currentDate)
    }

    fun onStartClick(fromHome: Boolean) {
        prepareChronometer()
        viewState.startWatch()
        if (fromHome) {
            status = 3
            viewState.startWorking(true)
        } else {
            status = 2
            viewState.startWorking(false)
        }
        // TODO: Save status to db
    }

    fun onStopClick() {
        viewState.stopWatch()
        status = 1
        viewState.stopWorking()
        // TODO: Save status to db
    }

    fun onChronometerTick(base: Long) {
        val time: Long = SystemClock.elapsedRealtime() - base
        workTimeInSec = time / 1000
        val h = (time / 3600000).toInt()
        val m = (time - h * 3600000).toInt() / 60000
        val s = (time - h * 3600000 - m * 60000).toInt() / 1000

        val workTime = ("0$h").takeLast(2) + " : " +
                ("0$m").takeLast(2) + " : " +
                ("0$s").takeLast(2)

        viewState.changeTimer(workTime)
        // TODO: Save worktime to db
    }

}