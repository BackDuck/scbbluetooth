package com.example.scbbluetooth.data.network.request

import com.example.scbbluetooth.data.network.pojo.body.AuthorizationBody
import com.example.scbbluetooth.data.network.pojo.body.RegistrationBody
import com.example.scbbluetooth.data.network.pojo.body.StateBody
import com.example.scbbluetooth.data.network.pojo.response.AuthorizationResponse
import com.example.scbbluetooth.data.network.pojo.response.StateResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Request {

    @POST("login")
    fun login(@Body body: AuthorizationBody): Single<Response<AuthorizationResponse>>

    @POST("registration")
    fun register(@Body body: RegistrationBody): Single<Response<Any>>

    @POST("state")
    fun setState(
        @Header("Authorization") token: String,
        @Body body: StateBody
    ): Single<StateResponse>

    @GET("state")
    fun getState(@Header("Authorization") token: String): Single<StateResponse>

}
