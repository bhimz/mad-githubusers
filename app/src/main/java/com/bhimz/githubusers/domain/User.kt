package com.bhimz.githubusers.domain

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val username: String,
    val profileLink: String,
    val name: String? = null,
    val email: String? = null,
    val avatarUrl: String? = null,
)