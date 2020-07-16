package com.example.scbbluetooth.data.network

import com.example.scbbluetooth.data.network.pojo.AuthorizationResponse
import com.example.scbbluetooth.data.network.pojo.StateResponse
import com.example.scbbluetooth.data.network.pojo.TokenResponse
import com.example.scbbluetooth.data.network.request.Request
import io.reactivex.Single

class ApiImpl(
    private val request: Request
) : Api {
    override fun login(authorization: AuthorizationResponse) = request.login(authorization)

    override fun state(token: TokenResponse, state: Int): Single<StateResponse> = request.state(token, state)

    override fun getState(token: TokenResponse): Single<StateResponse> = request.getState(token)

}
