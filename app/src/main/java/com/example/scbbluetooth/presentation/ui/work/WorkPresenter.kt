package com.example.scbbluetooth.presentation.ui.work

import android.os.SystemClock
import com.example.scbbluetooth.base.MoxyPresenter
import com.example.scbbluetooth.data.database.AppDatabase
import com.example.scbbluetooth.data.database.entity.BeaconEntity
import com.example.scbbluetooth.data.database.entity.StateEntity
import com.example.scbbluetooth.data.network.Api
import com.example.scbbluetooth.data.network.pojo.body.StateBody
import com.example.scbbluetooth.data.network.pojo.response.StateResponse
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import moxy.InjectViewState
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@InjectViewState
class WorkPresenter @Inject constructor() : MoxyPresenter<WorkView>() {

    private lateinit var token: String

    private lateinit var stateEntity: StateEntity

    private val mDisposable = CompositeDisposable()

    @Inject
    lateinit var api: Api

    @Inject
    lateinit var database: AppDatabase

    fun onFirstLaunch(token: String) {
        this.token = token
        getState()
    }

    fun prepareChronometer() {
        val calendar: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        val currentDate: String = sdf.format(calendar.time)
        viewState.refreshChrono(
            SystemClock.elapsedRealtime() - stateEntity.worktime * 1000,
            currentDate
        )

        /*
        -- Блок для автоматического запуска/остановки таймера в зависимости от ответа с сервера --
       ТОРМОЗИТ!
        when (stateEntity.state) {
            3 -> viewState.startWatch(true)
            2 -> viewState.startWatch(false)
            1 -> viewState.stopWatch()
        }*/
    }

    fun onStartClick(fromHome: Boolean) {
        if (fromHome) {
            stateEntity.state = 3
        } else {
            stateEntity.state = 2
        }
        updateState()
        viewState.startWatch(fromHome)
    }

    fun onStopClick() {
        stateEntity.state = 1
        updateState()
        viewState.stopWatch()
    }

    fun updateTime(base: Long) {
        val time: Long = SystemClock.elapsedRealtime() - base
        stateEntity.worktime = (time / 1000).toInt()
        val h = (time / 3600000).toInt()
        val m = (time - h * 3600000).toInt() / 60000
        val s = (time - h * 3600000 - m * 60000).toInt() / 1000

        val workTime = String.format("%02d : %02d : %02d", h, m, s)

        viewState.changeTimer(workTime)

        runBlocking(Dispatchers.Default) {
            database.stateDao().update(stateEntity)
        }
    }

    fun addBeacon(uuid: String, rssi: Int, major: Int, minor: Int) {
        val beaconEntity = BeaconEntity(0, uuid, rssi, major, minor)
        runBlocking(Dispatchers.Default) {
            database.beaconDao().insert(beaconEntity)
        }
    }

    fun isOnline(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return false
    }

    private fun getState() {
        mDisposable.add(Observable.interval(20, TimeUnit.SECONDS, Schedulers.io()).startWith(0)
            .observeOn(Schedulers.newThread())
            .map { _ ->
                api.getState(
                    token
                )
            }
            .retry()
            .subscribe { stateObservable ->
                stateObservable.subscribe { state ->
                    stateEntity = StateEntity(
                        0,
                        state.state_id,
                        state.work_time
                    )
                    runBlocking(Dispatchers.Default) {
                        database.stateDao().removeAll()
                        database.stateDao().insert(stateEntity)
                    }
                    runBlocking(Dispatchers.Main) {
                        prepareChronometer()
                    }
                }
            })
    }

    private fun updateState() {
        api.setState(
            token,
            StateBody(stateEntity.state)
        ).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .subscribe(object : SingleObserver<StateResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(state: StateResponse) {

                    stateEntity = StateEntity(
                        0,
                        state.state_id,
                        state.work_time
                    )

                    runBlocking(Dispatchers.Default) {
                        database.stateDao().removeAll()
                        database.stateDao().insert(stateEntity)
                    }

                    runBlocking(Dispatchers.Main) {
                        viewState.showError(state.state_id.toString())
                        prepareChronometer()
                    }
                }

                override fun onError(e: Throwable) {
                }
            })
    }

    override fun onDestroy() {
        mDisposable.clear()
        super.onDestroy()
    }
}