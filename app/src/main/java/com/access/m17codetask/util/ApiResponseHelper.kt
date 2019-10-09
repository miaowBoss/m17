package com.access.m17codetask.util

import android.arch.lifecycle.Observer
import com.access.m17codetask.api.ApiResponse
import io.reactivex.functions.Consumer

object ApiResponseHelper {

    fun <T : ApiResponse<V>, V> handle(
        onLoading: Consumer<Boolean>,
        onError: Consumer<Throwable>,
        onSuccess: Consumer<V>
    ): Observer<T> {

        return Observer {
            if (it != null) {
                try {
                    if (it.isLoading) {
                        onLoading.accept(true)
                    } else {
                        onLoading.accept(false)
                        if (it.isError) {
                            onError.accept(it.getThrowable())
                        } else {
                            onSuccess.accept(it.data)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }
}