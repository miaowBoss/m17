package com.access.m17codetask.api

import com.access.m17codetask.dataClass.UserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/users")
    fun getUserList(@Query("q") name: String, @Query("page") page: Int, @Query("perPage") perPage: Int): Single<UserResponse>
}