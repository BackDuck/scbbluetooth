package com.example.scbbluetooth.data.network

import com.example.scbbluetooth.data.network.pojo.AuthorizationResponse
import com.example.scbbluetooth.data.network.pojo.StateResponse
import com.example.scbbluetooth.data.network.pojo.TokenResponse
import io.reactivex.Single

interface Api {

    fun login(authorization: AuthorizationResponse): Single<TokenResponse>

    fun state(token: TokenResponse, state: Int): Single<StateResponse>

    fun getState(token: TokenResponse): Single<StateResponse>
}
