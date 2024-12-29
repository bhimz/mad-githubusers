package com.bhimz.githubusers.data


sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failed<T>(val error: Exception) : Result<T>()
}

fun <T> Result<T>.resultOrThrow(): T {
    return when (this) {
        is Result.Success -> this.data
        is Result.Failed -> throw this.error
    }
}