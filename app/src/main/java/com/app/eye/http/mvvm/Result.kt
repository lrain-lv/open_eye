package com.app.eye.http.mvvm

sealed class EyeResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : EyeResult<T>()
    data class Error(val exception: Exception) : EyeResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}