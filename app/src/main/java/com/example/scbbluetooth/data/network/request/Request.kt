package com.example.scbbluetooth.data.network.request

import com.example.scbbluetooth.data.network.pojo.AuthorizationResponse
import com.example.scbbluetooth.data.network.pojo.StateResponse
import com.example.scbbluetooth.data.network.pojo.TokenResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Request {

    @POST("login")
    fun login(@Body authorization: AuthorizationResponse): Single<TokenResponse>

    @POST("state")
    fun state(@Header("Authorization") token: TokenResponse,
              @Body state: Int): Single<StateResponse>

    @GET("state")
    fun getState(@Header("Authorization") token: TokenResponse): Single<StateResponse>

}
