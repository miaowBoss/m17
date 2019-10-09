package com.access.m17codetask.api

import android.arch.lifecycle.LiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ApiResponseLiveData<T>(private val observable: Single<T>) : LiveData<ApiResponse<T>>() {
    private var disposable: Disposable? = null

    override fun onActive() {
        super.onActive()
        if (disposable == null) {
            disposable = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { s -> setValue(ApiResponse()) }
                .subscribeWith(object : DisposableSingleObserver<T>() {
                    override fun onSuccess(@NonNull data: T) {
                        value = ApiResponse(data)
                    }

                    override fun onError(@NonNull e: Throwable) {
                        value = ApiResponse(e)
                    }
                })
        }
    }


    override fun onInactive() {
        super.onInactive()
        if (disposable != null) {
            disposable!!.dispose()
        }
    }
}