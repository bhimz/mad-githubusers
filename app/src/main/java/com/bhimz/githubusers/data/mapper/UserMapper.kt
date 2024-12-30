package com.bhimz.githubusers.data.mapper

import com.bhimz.githubusers.data.network.PagedUserResponse
import com.bhimz.githubusers.data.network.UserDetailResponse
import com.bhimz.githubusers.data.network.UserResponse
import com.bhimz.githubusers.domain.User
import com.bhimz.githubusers.domain.UserDetail
import java.text.SimpleDateFormat
import java.util.Locale

private const val format = "yyyy-MM-dd'T'HH:mm:ss'Z'"
val dateFormat = SimpleDateFormat(format, Locale.US)

val UserResponse.user: User
    get() = User(id, login, htmlUrl, name, email, avatarUrl)

val UserDetailResponse.userDetail: UserDetail
    get() = UserDetail(
        id = id,
        username = login,
        profileLink = htmlUrl,
        name = name,
        email = email,
        avatarUrl = avatarUrl,
        location = location,
        bio = bio,
        followers = followers ?: 0,
        following = following ?: 0,
        memberSince = dateFormat.parse(createdAt)
    )

val PagedUserResponse.users: List<User>
    get() = this.items.map { it.user }