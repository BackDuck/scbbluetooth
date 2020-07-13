package com.example.scbbluetooth.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface SvmApiService {

    @GET("portal_pg_mobile.authentication")
    fun getUser(
        @Query("p_login") login: String,
        @Query("p_pass") password: String
    ): Call<Worker>
}