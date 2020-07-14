package com.example.scbbluetooth.di.modules.retrofit

import com.example.scbbluetooth.presentation.ui.MockInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class RetrofitModule {
    private val baseUrl = "https://sovcombank.ru"

    @Provides
    @Singleton
    @Named("OkHttpClient")
    fun provideOkHttpClient(
        @Named("MockInterceptor") authInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    @Named("Retrofit")
    fun provideRetrofit(@Named("OkHttpClient") client: OkHttpClient,
                        gson: Gson
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    @Named("MockInterceptor")
    fun provideMockInterceptor(): Interceptor = MockInterceptor()
}