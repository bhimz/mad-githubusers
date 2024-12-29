package com.bhimz.githubusers.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bhimz.githubusers.data.GitUserRepository
import com.bhimz.githubusers.domain.User

class UserListPagingSource(
    private val userRepository: GitUserRepository
) : PagingSource<Long, User>() {
    override fun getRefreshKey(state: PagingState<Long, User>): Long? {
        return null
    }

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, User> {
        return try {
            val lastId = params.key
            val users = userRepository.fetch(lastId)
            LoadResult.Page(
                data = users,
                prevKey = null,
                nextKey = users.lastOrNull()?.id ?: -1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}