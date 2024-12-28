package com.bhimz.githubusers.data

import com.bhimz.githubusers.data.mapper.user
import com.bhimz.githubusers.data.network.GitApiService
import com.bhimz.githubusers.domain.User

interface GitUserRepository {
    suspend fun fetch(lastId: Long? = null): List<User>
}

class DefaultGitUserRepository(
    private val apiService: GitApiService
) : BaseRepository(), GitUserRepository {
    override suspend fun fetch(lastId: Long?): List<User> {
        return withApi { apiService.getUsers(lastId).map { it.user } }
    }
}

