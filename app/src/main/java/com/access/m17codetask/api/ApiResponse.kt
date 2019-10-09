package com.access.m17codetask.api

class ApiResponse<T> {

    var data: T? = null
    var error: Throwable? = null

    var isLoading: Boolean = false

    val isError: Boolean
        get() = error != null

    internal constructor() {
        isLoading = true
    }

    internal constructor(data: T) {
        this.data = data
    }

    internal constructor(error: Throwable) {
        this.error = error
    }

    fun getThrowable(): Throwable? {
        return when {
            error != null -> error
            data == null -> NullPointerException()
            else -> null
        }
    }

    fun setData(data: T): ApiResponse<T> {
        this.data = data
        this.isLoading = false
        return this
    }

    fun setError(error: Throwable): ApiResponse<T> {
        this.error = error
        this.isLoading = false
        return this
    }

}