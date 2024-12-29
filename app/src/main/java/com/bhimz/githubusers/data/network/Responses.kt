package com.bhimz.githubusers.data.network

data class UserResponse(
    val id: Long,
    val login: String,
    val url: String,
    val htmlUrl: String,
    val name: String?,
    val email: String?,
    val avatarUrl: String?,
)

data class PagedUserResponse(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val items: List<UserResponse>,
)

data class UserDetailResponse(
    val id: Long,
    val login: String,
    val url: String,
    val htmlUrl: String,
    val name: String?,
    val email: String?,
    val avatarUrl: String?,
    val location: String?,
    val bio: String?,
    val followers: Int?,
    val following: Int?,
    val createdAt: String
)