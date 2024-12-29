package com.bhimz.githubusers.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository {
    protected suspend fun <T> withApi(call: suspend () -> T): Result<T> =
        withContext(Dispatchers.IO) {
            try {
                val result = call.invoke()
                Result.Success(result)
            } catch (e: Exception) {
                Result.Failed(e)
            }
        }
}