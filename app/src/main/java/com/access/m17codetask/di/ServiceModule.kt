package com.access.m17codetask.di

import com.access.m17codetask.Config
import com.access.m17codetask.api.GithubService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module
class ServiceModule() {

    @Provides
    fun provideGithubService(): GithubService {
        val httpClient = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Config.API_PATH)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
        return retrofit.create(GithubService::class.java)
    }
}
