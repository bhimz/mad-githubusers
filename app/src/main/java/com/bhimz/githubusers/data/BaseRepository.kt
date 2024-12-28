package com.bhimz.githubusers.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository {
    protected suspend fun <T> withApi(call: suspend () -> T): T =
        withContext(Dispatchers.IO) {
            try {
                call.invoke()
            } catch (e: Exception) {
                throw e
            }
        }
}