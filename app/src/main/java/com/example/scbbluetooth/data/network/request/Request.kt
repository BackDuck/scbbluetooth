package com.example.scbbluetooth.data.network.request

import com.example.scbbluetooth.data.network.pojo.body.AuthorizationBody
import com.example.scbbluetooth.data.network.pojo.response.AuthorizationResponse
import com.example.scbbluetooth.data.network.pojo.body.RegistrationBody
import com.example.scbbluetooth.data.network.pojo.response.StateResponse
import com.example.scbbluetooth.data.network.pojo.body.TokenBody
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Request {

    @POST("login")
    fun login(@Body body: AuthorizationBody): Single<Response<AuthorizationResponse>>

    @POST("registration")
    fun register(@Body body: RegistrationBody): Single<Response<Any>>

    @POST("state")
    fun state(@Header("Authorization") token: TokenBody,
              @Body state: Int): Single<StateResponse>

    @GET("state")
    fun getState(@Header("Authorization") token: TokenBody): Single<Response<StateResponse>>

}
