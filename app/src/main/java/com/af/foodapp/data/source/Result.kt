package com.af.foodapp.data.source


sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val throwable: Throwable? = null) : Result<Nothing>()
}