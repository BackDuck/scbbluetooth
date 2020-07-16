package com.example.scbbluetooth.data.network

import com.example.scbbluetooth.data.network.pojo.StateResponse
import io.reactivex.Single

interface Api {

    fun login()

    fun state(token: String, state: Int): Single<StateResponse>

    fun getState(token: String): Single<StateResponse>

    fun setBeaconsData()
}
