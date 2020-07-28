package com.example.scbbluetooth.presentation.ui.work

import android.os.Handler
import android.os.SystemClock
import com.example.scbbluetooth.base.MoxyPresenter
import com.example.scbbluetooth.data.database.AppDatabase
import com.example.scbbluetooth.data.database.entity.BeaconEntity
import com.example.scbbluetooth.data.database.entity.StateEntity
import com.example.scbbluetooth.data.network.Api
import com.example.scbbluetooth.data.network.pojo.body.TokenBody
import com.example.scbbluetooth.data.network.pojo.response.StateResponse
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import moxy.InjectViewState
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject

@InjectViewState
class WorkPresenter @Inject constructor() : MoxyPresenter<WorkView>() {

    private lateinit var token: String

    private lateinit var stateEntity: StateEntity

    @Inject
    lateinit var api: Api

    @Inject
    lateinit var database: AppDatabase

    fun onFirstLaunch(token: String) {
        this.token = token
        getState(token)
    }

    fun prepareChronometer() {
        val calendar: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd.MM.yy")
        val currentDate: String = sdf.format(calendar.time)
        viewState.refreshChrono(
            SystemClock.elapsedRealtime() - stateEntity.worktime * 1000,
            currentDate
        )
    }

    fun onStartClick(fromHome: Boolean) {
        viewState.startWatch()
        if (fromHome) {
            stateEntity.state = 3
            viewState.startWorking(true)
        } else {
            stateEntity.state = 2
            viewState.startWorking(false)
        }
        runBlocking(Dispatchers.Default) {
            database.stateDao().update(stateEntity)
        }
    }

    fun onStopClick() {
        viewState.stopWatch()
        stateEntity.state = 1
        viewState.stopWorking()
        runBlocking(Dispatchers.Default) {
            database.stateDao().update(stateEntity)
        }
    }

    fun onChronometerTick(base: Long) {
        val time: Long = SystemClock.elapsedRealtime() - base
        stateEntity.worktime = (time / 1000).toInt()
        val h = (time / 3600000).toInt()
        val m = (time - h * 3600000).toInt() / 60000
        val s = (time - h * 3600000 - m * 60000).toInt() / 1000

        val workTime = ("0$h").takeLast(2) + " : " +
                ("0$m").takeLast(2) + " : " +
                ("0$s").takeLast(2)

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

    fun getState(token: String) {
        api.getState(
                TokenBody(token)
                ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Response<StateResponse>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(response: Response<StateResponse>) {
                    when (response.code()) {
                        200 -> {
                            stateEntity = StateEntity(
                                0,
                                response.body()!!.state_id,
                                response.body()!!.work_time
                            )
                            runBlocking(Dispatchers.Default) {
                                database.stateDao().removeAll()
                                database.stateDao().insert(stateEntity)
                            }
                            prepareChronometer()
                        }
                    }
                }

                override fun onError(e: Throwable) {
                }
            })
    }

    private fun updateState(stEntity: StateEntity) {
        api.getState(
            TokenBody(token)
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Response<StateResponse>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(response: Response<StateResponse>) {
                    when (response.code()) {
                        200 -> {
                            stateEntity = StateEntity(
                                0,
                                response.body()!!.state_id,
                                response.body()!!.work_time
                            )
                            runBlocking(Dispatchers.Default) {
                                database.stateDao().update(stateEntity)
                            }
                            prepareChronometer()
                        }
                    }
                }

                override fun onError(e: Throwable) {
                }
            })
    }
}