package com.example.scbbluetooth.data.network

import com.example.scbbluetooth.data.network.pojo.body.AuthorizationBody
import com.example.scbbluetooth.data.network.pojo.response.AuthorizationResponse
import com.example.scbbluetooth.data.network.pojo.body.RegistrationBody
import com.example.scbbluetooth.data.network.pojo.response.StateResponse
import com.example.scbbluetooth.data.network.pojo.body.TokenBody
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response

interface Api {

    fun login(body: AuthorizationBody): Single<Response<AuthorizationResponse>>

    fun register(body: RegistrationBody): Single<Response<Any>>

    fun state(token: TokenBody, state: Int): Single<StateResponse>

    fun getState(token: TokenBody): Single<Response<StateResponse>>
}
