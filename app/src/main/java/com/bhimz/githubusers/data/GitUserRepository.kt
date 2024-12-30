package com.bhimz.githubusers.data

import com.bhimz.githubusers.data.mapper.repo
import com.bhimz.githubusers.data.mapper.user
import com.bhimz.githubusers.data.mapper.userDetail
import com.bhimz.githubusers.data.mapper.users
import com.bhimz.githubusers.data.network.GitApiService
import com.bhimz.githubusers.data.network.UserResponse
import com.bhimz.githubusers.domain.Repo
import com.bhimz.githubusers.domain.User
import com.bhimz.githubusers.domain.UserDetail
import retrofit2.HttpException

interface GitUserRepository {
    suspend fun fetch(lastId: Long? = null): List<User>
    suspend fun get(username: String): UserDetail?
    suspend fun getRepo(username: String): List<Repo>
    suspend fun search(query: String, page: Int, itemPerPage: Int): List<User>
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

    override suspend fun get(username: String): UserDetail? {
        val response = withApi { apiService.getUser(username) }
        return when (response) {
            is Result.Success -> response.data.userDetail
            is Result.Failed -> {
                if (response.error is HttpException && response.error.code() == 404) {
                    null
                } else {
                    throw response.error
                }
            }
        }
    }

    override suspend fun getRepo(username: String): List<Repo> {
        return withApi { apiService.getUserRepo(username) }.resultOrThrow().map { it.repo }
    }

    override suspend fun search(query: String, page: Int, itemPerPage: Int): List<User> {
        return withApi { apiService.searchUsers(query, page, itemPerPage) }.resultOrThrow().users
    }
}

