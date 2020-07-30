package com.example.scbbluetooth.data.network

import com.example.scbbluetooth.data.network.pojo.body.AuthorizationBody
import com.example.scbbluetooth.data.network.pojo.body.RegistrationBody
import com.example.scbbluetooth.data.network.pojo.body.StateBody
import com.example.scbbluetooth.data.network.pojo.response.AuthorizationResponse
import com.example.scbbluetooth.data.network.pojo.response.StateResponse
import io.reactivex.Single
import retrofit2.Response

interface Api {

    fun login(body: AuthorizationBody): Single<Response<AuthorizationResponse>>

    fun register(body: RegistrationBody): Single<Response<Any>>

    fun setState(token: String, body: StateBody): Single<StateResponse>

    fun getState(token: String): Single<StateResponse>
}
