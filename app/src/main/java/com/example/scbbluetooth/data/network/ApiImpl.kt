package com.example.scbbluetooth.data.network

import com.example.scbbluetooth.data.network.pojo.StateResponse
import io.reactivex.Single

class ApiImpl(
    private val request: Request
) : Api {
    override fun login() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun state(token: String, state: Int): Single<StateResponse> =
        request.state(token, state)

    override fun getState(token: String): Single<StateResponse> = request.getState(token)

    override fun setBeaconsData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
