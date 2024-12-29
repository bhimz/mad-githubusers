package com.bhimz.githubusers.data

import com.bhimz.githubusers.data.mapper.user
import com.bhimz.githubusers.data.network.GitApiService
import com.bhimz.githubusers.data.network.UserResponse
import com.bhimz.githubusers.domain.User
import retrofit2.HttpException

interface GitUserRepository {
    suspend fun fetch(lastId: Long? = null): List<User>
    suspend fun get(username: String): User?
}

class DefaultGitUserRepository(
    private val apiService: GitApiService
) : BaseRepository(), GitUserRepository {
    override suspend fun fetch(lastId: Long?): List<User> {
        val response: List<UserResponse> = withApi {
            apiService.getUsers(lastId)
        }.resultOrThrow()
        return response.map { it.user }
    }

    override suspend fun get(username: String): User? {
        val response = withApi { apiService.getUser(username) }
        return when (response) {
            is Result.Success -> response.data.user
            is Result.Failed -> {
                if (response.error is HttpException && response.error.code() == 404) {
                    null
                } else {
                    throw response.error
                }
            }
        }
    }
}

