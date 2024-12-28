package com.bhimz.githubusers.di

import com.bhimz.githubusers.data.DefaultGitUserRepository
import com.bhimz.githubusers.data.GitUserRepository
import com.bhimz.githubusers.data.network.GitApiService
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Configurator {
    val gitUserRepository: GitUserRepository
        get() {
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
            val apiService = retrofit.create<GitApiService>()
            return DefaultGitUserRepository(apiService)
        }
}