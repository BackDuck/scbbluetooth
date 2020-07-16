package com.example.scbbluetooth.data.network

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
    ): Request = retrofit.create(Request::class.java)

    @Provides
    @Singleton
    fun provideApi(request: Request): Api = ApiImpl(request)


}
