package com.bhimz.githubusers

import com.bhimz.githubusers.data.DefaultGitUserRepository
import com.bhimz.githubusers.data.network.GitApiService
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class GithubApiServiceTest {

    private lateinit var apiService: GitApiService

    @Before
    fun setup() {
        val retrofit = Retrofit.Builder()
            .client(OkHttpClient())
            .addConverterFactory(
                GsonConverterFactory
                    .create(
                        GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                            .create()
                    )
            ).baseUrl("https://api.github.com").build()
        apiService = retrofit.create()
    }

    @Test
    fun `test get users`() {
        runBlocking {
            val response = apiService.getUsers(46)
            println(response)
        }
    }

    @Test
    fun `test get user`() {
        runBlocking {
            val response = apiService.getUser("joshknowles")
            println(response)
        }
    }

    @Test
    fun `test search users`() {
        runBlocking {
            val response = apiService.searchUsers("josh", page = 2)
            println(response)
        }
    }

    @Test
    fun `test repo`() {
        val repo = DefaultGitUserRepository(apiService)
        runBlocking {
            val result = repo.fetch()
            println(result)
        }
    }
}