package com.bhimz.githubusers.data.mapper

import com.bhimz.githubusers.data.network.UserResponse
import com.bhimz.githubusers.domain.User

val UserResponse.user: User
    get() = User(id, login, htmlUrl, name, email, avatarUrl)