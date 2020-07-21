package com.example.scbbluetooth.data.network

import com.example.scbbluetooth.data.network.pojo.body.AuthorizationBody
import com.example.scbbluetooth.data.network.pojo.response.AuthorizationResponse
import com.example.scbbluetooth.data.network.pojo.body.RegistrationBody
import com.example.scbbluetooth.data.network.pojo.response.StateResponse
import com.example.scbbluetooth.data.network.pojo.response.TokenResponse
import com.example.scbbluetooth.data.network.request.Request
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class ApiImpl(
    private val request: Request
) : Api {

    override fun login(body: AuthorizationBody): Single<Response<AuthorizationResponse>> = request.login(body)

    override fun register(body: RegistrationBody): Single<Response<Any>> = request.register(body)

    override fun state(token: TokenResponse, state: Int): Single<StateResponse> = request.state(token, state)

    override fun getState(token: TokenResponse): Single<StateResponse> = request.getState(token)

}
