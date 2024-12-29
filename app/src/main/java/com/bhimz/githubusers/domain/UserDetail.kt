package com.bhimz.githubusers.domain

import java.util.Date

data class UserDetail(
    val id: Long,
    val username: String,
    val profileLink: String,
    val name: String? = null,
    val email: String? = null,
    val avatarUrl: String? = null,
    val location: String? = null,
    val bio: String? = null,
    val followers: Int = 0,
    val following: Int = 0,
    val memberSince: Date?
)
