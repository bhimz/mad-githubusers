package com.bhimz.githubusers.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GitApiService {

    @GET("/users")
    suspend fun getUsers(@Query("since") since: Long? = null): List<UserResponse>

    @GET("/users/{username}")
    suspend fun getUser(@Path("username") username: String): UserDetailResponse

    @GET("/search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") pageCount: Int = 30
    ): PagedUserResponse
}