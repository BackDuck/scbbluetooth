package com.example.scbbluetooth.data.network

import com.example.scbbluetooth.data.network.request.Request
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideRequest(
        retrofit: Retrofit
    ): Request = retrofit.create(
        Request::class.java)

    @Provides
    @Singleton
    fun provideApi(request: Request): Api = ApiImpl(request)


}
