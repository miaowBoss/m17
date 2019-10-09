package com.access.m17codetask.viewModel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.access.m17codetask.api.ApiResponse
import com.access.m17codetask.dataClass.UserRequest
import com.access.m17codetask.dataClass.UserResponse
import com.access.m17codetask.repository.UserRepository

class MainViewModel : ViewModel() {
    var userRepository: UserRepository = UserRepository()

    val perPage = 10
    var page = 1
    var userResponseLiveData = MediatorLiveData<ApiResponse<UserResponse>>()
    val userRequestLiveData = MutableLiveData<UserRequest>()
    var mSearchName = ""

    init {
        userResponseLiveData.addSource(Transformations.switchMap(userRequestLiveData) { userRequest ->
            userRepository.getUserList(userRequest)
        }) { apiResponse ->
            userResponseLiveData.setValue(apiResponse)
        }

    }

    fun getUser(name: String) {
        mSearchName = name
        userRequestLiveData.value = UserRequest(mSearchName, page, perPage)
    }


    fun nextPage() {
        page++
        userRequestLiveData.value = UserRequest(mSearchName, page, perPage)
    }

}