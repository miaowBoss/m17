package com.access.m17codetask.repository

import android.arch.lifecycle.LiveData
import com.access.m17codetask.api.ApiResponse
import com.access.m17codetask.api.ApiResponseLiveData
import com.access.m17codetask.api.GithubService
import com.access.m17codetask.dataClass.UserRequest
import com.access.m17codetask.dataClass.UserResponse
import com.access.m17codetask.di.DaggerServiceComponent
import javax.inject.Inject


class UserRepository {
    init {
        DaggerServiceComponent
            .builder()
            .build()
            .inject(this)
    }

    @Inject
    lateinit var githubService: GithubService


    fun getUserList(userRequest: UserRequest): LiveData<ApiResponse<UserResponse>> {
        return ApiResponseLiveData(githubService.getUserList(userRequest.name, userRequest.page, userRequest.perPage))
    }

}
