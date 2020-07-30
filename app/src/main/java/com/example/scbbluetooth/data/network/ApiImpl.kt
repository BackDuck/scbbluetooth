package com.example.scbbluetooth.data.network

import com.example.scbbluetooth.data.network.pojo.body.AuthorizationBody
import com.example.scbbluetooth.data.network.pojo.body.RegistrationBody
import com.example.scbbluetooth.data.network.pojo.body.StateBody
import com.example.scbbluetooth.data.network.pojo.response.AuthorizationResponse
import com.example.scbbluetooth.data.network.pojo.response.StateResponse
import com.example.scbbluetooth.data.network.request.Request
import io.reactivex.Single
import retrofit2.Response

class ApiImpl(
    private val request: Request
) : Api {

    override fun login(body: AuthorizationBody): Single<Response<AuthorizationResponse>> =
        request.login(body)

    override fun register(body: RegistrationBody): Single<Response<Any>> = request.register(body)

    override fun setState(token: String, body: StateBody): Single<StateResponse> =
        request.setState(token, body)

    override fun getState(token: String): Single<StateResponse> = request.getState(token)

}
